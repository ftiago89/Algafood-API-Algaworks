package com.felipemelo.algafood.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(String msg){
        super(msg);
    }

    public KitchenNotFoundException(Long kitchenId){
        this(String.format("Kitchen with code %d not found.", kitchenId));
    }
}
