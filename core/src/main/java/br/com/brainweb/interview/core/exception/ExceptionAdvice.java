package br.com.brainweb.interview.core.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<Object> handleGeneralException(Exception e) {
        log.error("Uncaught exception, message={}", e.getMessage(), e);
        return status(INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<Object> handleInvalidRequest(Exception e) {
        return status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = {InvalidFormatException.class, HttpMessageNotReadableException.class})
    ResponseEntity<Object> handleInvalidFormatException(Exception e) {
        log.warn(e.getMessage());
        return status(BAD_REQUEST).body("message.malformed-request");
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return status(METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleConstraintViolationException(MethodArgumentNotValidException e) {
        final List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        log.warn(e.getMessage());
        return status(FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn(e.getMessage());
        return status(UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(value = {HttpStatusCodeException.class})
    ResponseEntity<Object> handleHttpStatusCodeException(HttpStatusCodeException e) {
        if (e.getStatusCode().is5xxServerError()) {
            log.error("RestTemplate communication error message={}", e.getResponseBodyAsString(), e);
        }
        if (e.getStatusCode().is4xxClientError()) {
            final String errorMessage = e.getResponseBodyAsString();
            if (!StringUtils.isEmpty(errorMessage)) {
                log.warn(errorMessage);
            }
        }
        if (e.getStatusCode().equals(UNAUTHORIZED)) {
            return status(e.getStatusCode()).headers(e.getResponseHeaders()).build();
        }
        return status(e.getStatusCode()).body(e.getResponseBodyAsString());
    }

    @ExceptionHandler(ResourceAccessException.class)
    ResponseEntity<Object> handleResourceAccessException(ResourceAccessException e) {
        log.error(e.getMessage(), e);
        return status(BAD_GATEWAY).body("message.integration.connection.refused");
    }

    @ExceptionHandler(GenericException.class)
    ResponseEntity<Object> handleGenericException(GenericException e){
        log.error(e.getResponseMessage(), e);
        return status(e.getStatus()).body( Map.of("message", e.getResponseMessage()));
    }
}
