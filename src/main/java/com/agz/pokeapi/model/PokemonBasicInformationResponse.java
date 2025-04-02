package com.agz.pokeapi.model;

import java.util.List;

public record PokemonBasicInformationResponse(
        int id,
        String name,
        String image_url,
        int weight,
        List<String> type,
        List<String> abilities

) {
}
