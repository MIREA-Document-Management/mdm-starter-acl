package ru.mdm.acl.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.acl.model.dto.AccessRuleDto;
import ru.mdm.acl.model.dto.CreateAccessRuleDto;
import ru.mdm.acl.model.dto.UpdateAccessRuleDto;
import ru.mdm.acl.rest.api.AccessRuleApi;
import ru.mdm.acl.service.AccessRuleService;

import java.util.UUID;

/**
 * Контроллер для работы с правилами доступа.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AccessRuleApi.BASE_URI)
public class AccessRuleController implements AccessRuleApi {

    private final AccessRuleService accessRuleService;

//    @Override
//    public Mono<AccessRuleDto> createAccessRule(CreateAccessRuleDto dto) {
//        return accessRuleService.createAccessRule(dto);
//    }

    @Override
    public Mono<AccessRuleDto> updateAccessRule(UUID ruleId, UpdateAccessRuleDto dto) {
        return accessRuleService.updateAccessRule(ruleId, dto);
    }

    @Override
    public Flux<AccessRuleDto> getObjectAccessRules(UUID objectId, Pageable pageable) {
        return accessRuleService.getObjectAccessRules(objectId, pageable);
    }

    @Override
    public Mono<AccessRuleDto> getAccessRuleDto(UUID ruleId) {
        return accessRuleService.getAccessRuleDto(ruleId);
    }

    @Override
    public Mono<AccessRuleDto> deleteAccessRule(UUID ruleId) {
        return accessRuleService.deleteAccessRule(ruleId);
    }

    @Override
    public Flux<AccessRuleDto> deleteAccessRules(UUID objectId) {
        return accessRuleService.deleteAccessRules(objectId);
    }
}
