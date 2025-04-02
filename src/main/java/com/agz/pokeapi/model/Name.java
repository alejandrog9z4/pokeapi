package com.agz.pokeapi.model;

public record Name(
        String name,
        NamedAPIResource language
) {
}
