package com.felipemelo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    ILEGIBLE_REQUEST("/ilegible-request", "Ilegible request"),
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation");

    private final String title;
    private final String uri;

    ErrorType(String path, String title){
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
