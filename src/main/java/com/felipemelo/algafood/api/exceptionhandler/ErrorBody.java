package com.felipemelo.algafood.api.exceptionhandler;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorBody {

    private final Integer status;
    private final String type;
    private final String title;
    private final String detail;

    private String userMessage;
    private LocalDateTime timeStamp;
}
