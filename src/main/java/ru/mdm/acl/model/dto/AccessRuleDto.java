package ru.mdm.acl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.mdm.acl.model.RuleType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Модель правила доступа")
public class AccessRuleDto {

    @Schema(description = "Идентификатор правила")
    private UUID id;

    @Schema(description = "Идентификатор объекта, на который распространяется правило")
    private UUID objectId;

    @Schema(description = "Действие")
    private String action;

    @Schema(description = "Тип правила (разрешающий/запрещающий)")
    private RuleType ruleType;

    @Schema(description = "Идентификаторы ролей, на которые распространяется правило")
    private List<String> roles;

    @Schema(description = "Идентификаторы групп, на которые распространяется правило")
    private List<String> groups;

    @Schema(description = "Идентификаторы пользователей, на которые распространяется правило")
    private List<String> users;

    @Schema(description = "Кто создал правило")
    private String createdBy;

    @Schema(description = "Дата и время создания")
    private LocalDateTime creationDate;

    @Schema(description = "Кто последний изменил правило")
    private String modifiedBy;

    @Schema(description = "Дата и время последнего изменения")
    private LocalDateTime modificationDate;
}
