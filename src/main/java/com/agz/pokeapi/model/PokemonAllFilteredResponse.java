package com.agz.pokeapi.model;

import java.util.List;

public record PokemonAllFilteredResponse(
        int count,
        String next,
        String previous,
        List<PokemonBasicInformationResponse> results
) {
}
