package com.felipemelo.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorBody {

    private final LocalDateTime dateTime;
    private final String message;
}
