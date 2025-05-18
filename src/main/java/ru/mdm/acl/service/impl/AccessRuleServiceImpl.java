package ru.mdm.acl.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.acl.configuration.AclStarterProperties;
import ru.mdm.acl.exception.AclBadRequestException;
import ru.mdm.acl.exception.AclErrorCode;
import ru.mdm.acl.exception.AclForbiddenException;
import ru.mdm.acl.exception.AclNotFoundException;
import ru.mdm.acl.model.RuleType;
import ru.mdm.acl.model.dto.AccessRuleDto;
import ru.mdm.acl.model.dto.CreateAccessRuleDto;
import ru.mdm.acl.model.dto.UpdateAccessRuleDto;
import ru.mdm.acl.model.entity.AccessRule;
import ru.mdm.acl.model.mapper.AccessRuleMapper;
import ru.mdm.acl.repository.AccessRuleRepository;
import ru.mdm.acl.service.AccessRuleService;
import ru.mdm.acl.service.AclObjectService;
import ru.mdm.acl.util.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

/**
 * Реализация сервиса для работы с правилами доступа.
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AccessRuleServiceImpl implements AccessRuleService {

    private final AccessRuleRepository repository;
    private final AccessRuleMapper mapper;
    private final AclStarterProperties starterProperties;
    private final AclObjectService objectService;

    @Override
    public Mono<AccessRuleDto> createAccessRule(CreateAccessRuleDto dto) {
        return checkIsObjectOwner(dto.getObjectId())
                .then(validateAction(dto.getAction()))
                .then(checkIfRuleAlreadyExists(dto))
                .thenReturn(dto)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @Override
    public Mono<AccessRuleDto> updateAccessRule(UUID ruleId, UpdateAccessRuleDto dto) {
        return checkIfNotRuleOwner(ruleId)
                .then(repository.findById(ruleId))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AclNotFoundException(AclErrorCode.RULE_NOT_FOUND.buildErrorText(ruleId)))))
                .map(rule -> mapper.updateEntity(rule, dto))
                .flatMap(repository::save)
                .map(mapper::toDto);
    }

    @Override
    public Flux<AccessRuleDto> getObjectAccessRules(UUID objectId, Pageable pageable) {
        return repository.findFirstByObjectId(objectId)
                .flatMap(rule -> checkIfNotRuleOwner(rule.getId()).thenReturn(rule))
                .thenMany(repository.findByObjectId(objectId, pageable))
                .map(mapper::toDto);
    }

    @Override
    public Mono<AccessRuleDto> getAccessRuleDto(UUID ruleId) {
        return checkIfNotRuleOwner(ruleId)
                .then(repository.findById(ruleId))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AclNotFoundException(AclErrorCode.RULE_NOT_FOUND.buildErrorText(ruleId)))))
                .map(mapper::toDto);
    }

    @Override
    public Mono<AccessRuleDto> deleteAccessRule(UUID ruleId) {
        return checkIfNotRuleOwner(ruleId)
                .then(repository.findById(ruleId))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AclNotFoundException(AclErrorCode.RULE_NOT_FOUND.buildErrorText(ruleId)))))
                .map(mapper::toDto)
                .flatMap(dto -> repository.deleteById(ruleId).thenReturn(dto));
    }

    @Override
    public Flux<AccessRuleDto> deleteAccessRules(UUID objectId) {
        return checkIsObjectOwner(objectId)
                .thenMany(repository.findByObjectId(objectId, Pageable.unpaged()))
                .transform(rules -> repository.deleteByObjectId(objectId).thenMany(rules))
                .map(mapper::toDto);
    }

    @Override
    public Mono<Void> checkPermission(UUID objectId, String action) {
        return validateAction(action)
                .onErrorMap(AclBadRequestException.class, throwable -> new AclForbiddenException())
                .then(SecurityContextHolder.getUserLogin())
                .flatMap(login -> repository.findByObjectIdAndAction(objectId, action)
                        .switchIfEmpty(Mono.defer(() -> {
                            log.warn("checkPermission: User with login {} has no access to object with id={} with action {}", login, objectId, action);
                            return Mono.error(new AclForbiddenException());
                        })))
                .flatMap(this::checkHasPermission);
    }

    private Mono<Void> validateAction(String action) {
        if (starterProperties.getActions().contains(action)) {
            return Mono.empty();
        }
        return Mono.error(new AclBadRequestException(AclErrorCode.ACTION_NOT_PRESENT.buildErrorText(action)));
    }

    private Mono<Void> checkIfRuleAlreadyExists(CreateAccessRuleDto dto) {
        return repository.findByObjectIdAndAction(dto.getObjectId(), dto.getAction())
                .flatMap(ignore -> Mono.error(
                        new AclBadRequestException(AclErrorCode.RULE_WITH_ACTION_ALREADY_EXISTS
                                .buildErrorText(dto.getAction(), dto.getObjectId()))));
    }

    private Mono<Void> checkIfNotRuleOwner(UUID ruleId) {
        return SecurityContextHolder.getUserLogin()
                .flatMap(login -> repository.findById(ruleId)
                        .filter(rule -> rule.getCreatedBy().equals(login)))
                .switchIfEmpty(Mono.error(new AclForbiddenException()))
                .then();
    }

    private Mono<Void> checkIsObjectOwner(UUID objectId) {
        return SecurityContextHolder.getUserLogin()
                .flatMap(login -> objectService.getOwnerLogin(objectId)
                        .filter(owner -> owner.equals(login)))
                .switchIfEmpty(Mono.error(new AclForbiddenException()))
                .then();
    }

    private Mono<Void> checkHasPermission(AccessRule rule) {
        return SecurityContextHolder.getUserLogin()
                .zipWith(SecurityContextHolder.getUserRoles().collectList())
                .zipWith(SecurityContextHolder.getUserGroups().collectList())
                .flatMap(objects -> {
                    String login = objects.getT1().getT1();
                    List<String> roles = objects.getT1().getT2();
                    List<String> groups = objects.getT2();

                    if (roles.contains("mdm-admin")) {
                        return Mono.empty();
                    }

                    if (rule.getCreatedBy().equals(login)) {
                        return Mono.empty();
                    }

                    if (rule.getUsers() != null && !rule.getUsers().isEmpty()) {
                        if (rule.getUsers().contains(login)) {
                            return (RuleType.ALLOW.equals(rule.getRuleType())) ? Mono.empty() : Mono.error(new AclForbiddenException());
                        }
                    }

                    if (rule.getGroups() != null && !rule.getGroups().isEmpty()) {
                        if (rule.getGroups().stream().anyMatch(groups::contains)) {
                            return (RuleType.ALLOW.equals(rule.getRuleType())) ? Mono.empty() : Mono.error(new AclForbiddenException());
                        }
                    }

                    if (rule.getRoles() != null && !rule.getRoles().isEmpty()) {
                        if (rule.getRoles().stream().anyMatch(roles::contains)) {
                            return (RuleType.ALLOW.equals(rule.getRuleType())) ? Mono.empty() : Mono.error(new AclForbiddenException());
                        }
                    }

                    return Mono.error(new AclForbiddenException());
                });
    }
}
