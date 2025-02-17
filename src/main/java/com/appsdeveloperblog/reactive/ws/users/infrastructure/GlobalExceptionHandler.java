package com.appsdeveloperblog.reactive.ws.users.infrastructure;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice //use @ControllerAdvice and @ResponseBody applies to all Layers
public class GlobalExceptionHandler {

    //Handle specific exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ErrorResponse> DuplicateKeyException(DataIntegrityViolationException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT, exception.getMessage())
                .build());
    }

    //Handle general exceptions
    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGeneralExceptions(Exception exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage())
                .build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
        //building an error response
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, errorMessage)
                .build());
    }
}
