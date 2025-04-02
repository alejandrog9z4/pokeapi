package com.agz.pokeapi.model;

import java.util.List;

public record PokemonHeldItems(
        NamedAPIResource item,
        List<PokemonVersionDetails> version_details
) {
}
