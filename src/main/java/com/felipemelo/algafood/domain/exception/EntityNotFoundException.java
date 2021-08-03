package com.felipemelo.algafood.domain.exception;

public abstract class EntityNotFoundException extends BusinessException {
    private static final long serialVersionUID = 1L;

    protected EntityNotFoundException(String msg){
        super(msg);
    }
}
