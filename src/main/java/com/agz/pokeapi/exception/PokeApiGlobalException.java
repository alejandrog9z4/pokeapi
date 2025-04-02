package com.agz.pokeapi.exception;

import feign.FeignException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PokeApiGlobalException {
    Logger logger = org.slf4j.LoggerFactory.getLogger(PokeApiGlobalException.class);

    @ExceptionHandler(PokeApiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePokemonNotFoundException(PokeApiException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleFeignException(FeignException ex) {
        return new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                "External API error: " + ex.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        logger.error("Unexpected error: ", ex);
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                LocalDateTime.now()
        );
    }
}
