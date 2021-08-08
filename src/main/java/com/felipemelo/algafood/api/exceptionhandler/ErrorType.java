package com.felipemelo.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    ILLEGIBLE_REQUEST("/ilegible-request", "Ilegible request"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    BUSINESS_ERROR("/business-error", "Business rule violation"),
    SYSTEM_ERROR("/system-error", "System error");

    private final String title;
    private final String uri;

    ErrorType(String path, String title){
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }
}
