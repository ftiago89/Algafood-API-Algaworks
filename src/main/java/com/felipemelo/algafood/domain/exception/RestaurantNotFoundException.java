package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(String msg){
        super(msg);
    }

    public RestaurantNotFoundException(Long restaurantId){
        this(String.format("Restaurant with code %d not found.", restaurantId));
    }
}
