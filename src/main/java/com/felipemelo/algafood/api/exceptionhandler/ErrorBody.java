package com.felipemelo.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorBody {

    private final Integer status;
    private final LocalDateTime timeStamp;
    private final String type;
    private final String title;
    private final String detail;
    private final String userMessage;
    private final List<Field> fields;

    @Getter
    @Builder
    public static class Field{

        private final String name;
        private final String userMessage;
    }

}
