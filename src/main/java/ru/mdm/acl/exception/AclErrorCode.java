package ru.mdm.acl.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AclErrorCode {

    ACTION_NOT_PRESENT("Действие %s не присутствует в конфигурации сервиса"),
    RULE_WITH_ACTION_ALREADY_EXISTS("Правило с действием %s и идентфикатором объекта %s уже существует"),
    RULE_NOT_FOUND("Правило с id %s не найдено")
    ;

    private final String text;

    public String buildErrorText(Object... params) {
        return String.format(this.getText(), params);
    }
}
