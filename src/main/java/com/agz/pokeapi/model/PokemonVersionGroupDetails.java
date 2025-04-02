package com.agz.pokeapi.model;

public record PokemonVersionGroupDetails(
        int level_learned_at,
        NamedAPIResource version_group,
        NamedAPIResource move_learn_method
) {
}
