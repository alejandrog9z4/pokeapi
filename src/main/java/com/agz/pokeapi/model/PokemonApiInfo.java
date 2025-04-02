package com.agz.pokeapi.model;

import java.util.List;

public record PokemonApiInfo(
        int id,
        String name,
        int base_experience,
        boolean is_default,
        int height,
        int weight,
        int order,
        List<PokemonAbility> abilities,
        List<NamedAPIResource> forms,
        List<PokemonGameIndex> game_indices,
        List<PokemonHeldItems> held_items,
        String location_area_encounters,
        List<PokemonMove> moves,
        NamedAPIResource species,
        PokemonSprites sprites,
        PokemonCries cries,
        List<PokemonStats> stats,
        List<PokemonTypes> types,
        List<PokemonPastTypes> past_types
) {
}
