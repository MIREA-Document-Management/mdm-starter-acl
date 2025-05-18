package ru.mdm.acl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение для запрета операции.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AclForbiddenException extends RuntimeException {

    public AclForbiddenException() {
        super("Нет доступа на выполение данной операции.");
    }
}
