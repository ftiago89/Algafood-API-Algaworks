package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StateNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String msg){
        super(msg);
    }

    public StateNotFoundException(Long stateId){
        this(String.format("State with code %d not found.", stateId));
    }
}
