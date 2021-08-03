package com.felipemelo.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String msg){
        super(msg);
    }

    public CityNotFoundException(Long cityId){
        this(String.format("City with code %d not found.", cityId));
    }
}
