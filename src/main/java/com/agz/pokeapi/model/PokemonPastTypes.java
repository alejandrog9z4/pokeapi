package com.agz.pokeapi.model;

import java.util.List;

public record PokemonPastTypes(
        NamedAPIResource generation,
        List<PokemonTypes> types
) {
}
