package com.felipemelo.algafood.api.exceptionhandler;

import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String detail = "Request body is invalid. Verify possible syntax problems";

        var errorBody = createErrorBodyBuild(status, ErrorType.ILEGIBLE_REQUEST,
                detail).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){

        var errorBody = createErrorBodyBuild(HttpStatus.NOT_FOUND, ErrorType.ENTITY_NOT_FOUND,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request){

        var errorBody = createErrorBodyBuild(HttpStatus.CONFLICT, ErrorType.ENTITY_IN_USE,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request){

        var errorBody = createErrorBodyBuild(HttpStatus.BAD_REQUEST, ErrorType.BUSINESS_ERROR,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers
            , HttpStatus status, WebRequest request) {

        if (body == null){
            body = ErrorBody.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String){
            body = ErrorBody.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ErrorBody.ErrorBodyBuilder createErrorBodyBuild(HttpStatus status, ErrorType errorType, String detail){
        return ErrorBody.builder()
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail);
    }
}
