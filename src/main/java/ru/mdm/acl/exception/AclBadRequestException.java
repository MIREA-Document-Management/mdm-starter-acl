package ru.mdm.acl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AclBadRequestException extends RuntimeException {

    public AclBadRequestException(String message) {
        super(message);
    }
}
