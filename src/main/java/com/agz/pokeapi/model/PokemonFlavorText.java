package com.agz.pokeapi.model;

public record PokemonFlavorText(
        String flavor_text,
        NamedAPIResource language,
        NamedAPIResource version
) {
}
