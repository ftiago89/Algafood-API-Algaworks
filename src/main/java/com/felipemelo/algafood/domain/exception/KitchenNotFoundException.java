package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KitchenNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(String msg){
        super(msg);
    }

    public KitchenNotFoundException(Long kitchenId){
        this(String.format("Kitchen with code %d not found.", kitchenId));
    }
}
