package com.agz.pokeapi.exception;

public class PokeApiException extends RuntimeException{
    public PokeApiException(String id){
        super(String.format("Pokemon with id %s not found", id));
    }
}
