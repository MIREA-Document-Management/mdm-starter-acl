package ru.mdm.acl.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.acl.model.dto.AccessRuleDto;
import ru.mdm.acl.model.dto.CreateAccessRuleDto;
import ru.mdm.acl.model.dto.UpdateAccessRuleDto;

import java.util.UUID;

/**
 * Сервис для работы с правилами доступа.
 */
public interface AccessRuleService {

    /**
     * Создать правило доступа.
     *
     * @param dto модель для создания правила
     * @return созданное правило
     */
    Mono<AccessRuleDto> createAccessRule(@Valid CreateAccessRuleDto dto);

    /**
     * Обновить правило доступа.
     *
     * @param ruleId идентификатор правила доступа
     * @param dto модель с обновленными параметрами
     * @return обновленное правило
     */
    Mono<AccessRuleDto> updateAccessRule(@NotNull UUID ruleId, @Valid UpdateAccessRuleDto dto);

    /**
     * Получить список правил для объекта.
     *
     * @param objectId идентификатор объекта
     * @param pageable параметры страницы
     * @return список правил
     */
    Flux<AccessRuleDto> getObjectAccessRules(@NotNull UUID objectId, Pageable pageable);

    /**
     * Получить правило по идентификатору.
     *
     * @param ruleId идентификатор правила
     * @return найденное правило
     */
    Mono<AccessRuleDto> getAccessRuleDto(@NotNull UUID ruleId);

    /**
     * Удалить правило по идентификатору.
     *
     * @param ruleId идентификатор правила
     * @return удаленное правило
     */
    Mono<AccessRuleDto> deleteAccessRule(@NotNull UUID ruleId);

    /**
     * Удалить все правило объекта.
     *
     * @param objectId идентификатор объекта
     * @return список удаленных правил
     */
    Flux<AccessRuleDto> deleteAccessRules(@NotNull UUID objectId);

    /**
     * Проверить, есть ли разрешение на выполнение действия над объектом.
     *
     * @param objectId идентификатор объекта
     * @param action действие
     * @throws ru.mdm.acl.exception.AclForbiddenException если доступ запрещен
     */
    Mono<Void> checkPermission(@NotNull UUID objectId, @NotBlank String action);
}
