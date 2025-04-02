package com.agz.pokeapi.model;

public record PokemonSpeciesVariety(
        NamedAPIResource pokemon,
        boolean is_default
) {
}
