package com.appsdeveloperblog.reactive.ws.users.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Mono;

@RestControllerAdvice //use @ControllerAdvice and @ResponseBody applies to all Layers
public class GlobalExceptionHandler {

    //Handle specific exception
    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ErrorResponse> DuplicateKeyException(DuplicateKeyException exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT, exception.getMessage()).build());
    }

    //Handle general exceptions
    /*@ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGeneralExceptions(Exception exception) {
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).build());
    }*/
}
