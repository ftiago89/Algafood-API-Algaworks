package com.felipemelo.algafood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String msg){
        super(msg);
    }

    public StateNotFoundException(Long stateId){
        this(String.format("State with code %d not found.", stateId));
    }
}
