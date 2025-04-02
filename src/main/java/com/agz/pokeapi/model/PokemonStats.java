package com.agz.pokeapi.model;

public record PokemonStats(
        int base_stat,
        int effort,
        NamedAPIResource stat
) {
}
