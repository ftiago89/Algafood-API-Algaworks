package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntityNotFoundException extends BusinessException {
    private static final long serialVersionUID = 1L;

    protected EntityNotFoundException(String msg){
        super(msg);
    }
}
