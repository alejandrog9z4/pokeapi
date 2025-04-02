package com.agz.pokeapi.model;

import java.util.List;

public record PokemonDetailResponse(
        int id,
        String name,
        int weight,
        String image,
        List<String> type,
        List<String> abilities,
        String description,
        List<String> evolutions

) {
}
