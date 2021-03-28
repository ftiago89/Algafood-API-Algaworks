package com.felipemelo.algafood.domain.exception;

public class EntityInUseException extends RuntimeException{

    public EntityInUseException(String msg){
        super(msg);
    }
}
