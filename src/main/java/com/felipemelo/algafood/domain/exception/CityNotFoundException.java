package com.felipemelo.algafood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String msg){
        super(msg);
    }

    public CityNotFoundException(Long cityId){
        this(String.format("City with code %d not found.", cityId));
    }
}
