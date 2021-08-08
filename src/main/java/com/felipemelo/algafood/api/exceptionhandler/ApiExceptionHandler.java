package com.felipemelo.algafood.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.felipemelo.algafood.domain.exception.BusinessException;
import com.felipemelo.algafood.domain.exception.EntityInUseException;
import com.felipemelo.algafood.domain.exception.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {

        String detail = String.format("Resource '%s' not found", ex.getRequestURL());
        var errorBody = createErrorBodyBuild(status, ErrorType.RESOURCE_NOT_FOUND, detail).build();

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                    HttpHeaders headers, HttpStatus status,
                                                                    WebRequest request) {

        String detail = String.format("URL parameter '%s' received value '%s' which is not valid. Use a compatible " +
                "%s type", ex.getName(), ex.getValue(), ex.getParameter().getParameterType().getSimpleName());
        var errorBody = createErrorBodyBuild(status, ErrorType.INVALID_PARAMETER, detail).build();

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        var rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        String detail = "Request body is invalid. Verify possible syntax problems";
        var errorBody = createErrorBodyBuild(status, ErrorType.ILLEGIBLE_REQUEST,
                detail).build();

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());
        String detail = String.format("Field '%s' with invalid value '%s'. Please correct with a valid value of type " +
                "%s", path, ex.getValue(), ex.getTargetType().getSimpleName());

        var errorBody = createErrorBodyBuild(status, ErrorType.ILLEGIBLE_REQUEST,
                detail).build();

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        String path = joinPath(ex.getPath());
        String detail = String.format("Field '%s' does not exist", path);

        var errorBody = createErrorBodyBuild(status, ErrorType.ILLEGIBLE_REQUEST,
                detail).build();

        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {

        var errorBody = createErrorBodyBuild(HttpStatus.NOT_FOUND, ErrorType.RESOURCE_NOT_FOUND,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<Object> handleEntityInUse(EntityInUseException ex, WebRequest request) {

        var errorBody = createErrorBodyBuild(HttpStatus.CONFLICT, ErrorType.ENTITY_IN_USE,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {

        var errorBody = createErrorBodyBuild(HttpStatus.BAD_REQUEST, ErrorType.BUSINESS_ERROR,
                ex.getMessage()).build();

        return handleExceptionInternal(ex, errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers
            , HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ErrorBody.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = ErrorBody.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ErrorBody.ErrorBodyBuilder createErrorBodyBuild(HttpStatus status, ErrorType errorType, String detail) {
        return ErrorBody.builder()
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<JsonMappingException.Reference> references){
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}
