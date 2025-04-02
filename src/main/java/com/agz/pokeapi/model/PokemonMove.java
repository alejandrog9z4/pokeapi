package com.agz.pokeapi.model;

import java.util.List;

public record PokemonMove(
        NamedAPIResource move,
        List<PokemonVersionGroupDetails> version_group_details
) {
}
