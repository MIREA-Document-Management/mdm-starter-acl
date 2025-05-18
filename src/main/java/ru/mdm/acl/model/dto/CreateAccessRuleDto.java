package ru.mdm.acl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.mdm.acl.model.RuleType;

import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "Модель для создания правила доступа")
public class CreateAccessRuleDto {

    @Schema(description = "Идентификатор объекта, на который распространяется правило")
    @NotNull(message = "Отсутствует идентификатор документа")
    private UUID objectId;

    @Schema(description = "Действие")
    @NotNull(message = "Отсутствует действие над объектом")
    private String action;

    @Schema(description = "Тип правила (разрешающий/запрещающий)")
    @NotNull(message = "Отсутствует тип правила")
    private RuleType ruleType;

    @Schema(description = "Идентификаторы ролей, на которые распространяется правило")
    private List<String> roles;

    @Schema(description = "Идентификаторы групп, на которые распространяется правило")
    private List<String> groups;

    @Schema(description = "Идентификаторы пользователей, на которые распространяется правило")
    private List<String> users;
}
