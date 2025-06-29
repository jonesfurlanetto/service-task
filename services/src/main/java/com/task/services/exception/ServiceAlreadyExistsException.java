package com.task.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ServiceAlreadyExistsException extends RuntimeException {

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }
}
