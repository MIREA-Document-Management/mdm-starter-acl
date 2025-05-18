package ru.mdm.acl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AclNotFoundException extends RuntimeException {

    public AclNotFoundException(String message) {
        super(message);
    }
}
