package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(HttpStatus status, String reason){
        super(status, reason);
    }

    public EntityNotFoundException(String msg){
        this(HttpStatus.NOT_FOUND, msg);
    }
}
