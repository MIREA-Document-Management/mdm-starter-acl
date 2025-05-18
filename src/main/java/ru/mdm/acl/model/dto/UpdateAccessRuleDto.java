package ru.mdm.acl.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.mdm.acl.model.RuleType;

import java.util.List;

@Data
@Schema(description = "Модель для обновления правила доступа")
public class UpdateAccessRuleDto {

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
