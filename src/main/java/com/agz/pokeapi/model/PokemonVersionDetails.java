package com.agz.pokeapi.model;

public record PokemonVersionDetails(
        int rarity,
        NamedAPIResource version
) {
}
