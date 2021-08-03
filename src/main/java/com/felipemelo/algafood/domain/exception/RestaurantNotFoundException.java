package com.felipemelo.algafood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(String msg){
        super(msg);
    }

    public RestaurantNotFoundException(Long restaurantId){
        this(String.format("Restaurant with code %d not found.", restaurantId));
    }
}
