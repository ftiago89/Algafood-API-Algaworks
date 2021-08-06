package com.felipemelo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found");

    private final String title;
    private final String uri;

    ErrorType(String path, String title){
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
