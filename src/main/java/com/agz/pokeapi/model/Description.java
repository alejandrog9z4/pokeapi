package com.agz.pokeapi.model;

public record Description(
        String description,
        NamedAPIResource language
) {
}
