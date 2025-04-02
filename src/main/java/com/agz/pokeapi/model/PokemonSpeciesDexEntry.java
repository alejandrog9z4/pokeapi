package com.agz.pokeapi.model;

public record PokemonSpeciesDexEntry(
        int entry_number,
        NamedAPIResource pokedex
) {
}
