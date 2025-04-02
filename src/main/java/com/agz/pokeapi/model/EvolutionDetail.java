package com.agz.pokeapi.model;

public record EvolutionDetail(
        NamedAPIResource item,
        NamedAPIResource trigger,
        int gender,
        NamedAPIResource held_item,
        NamedAPIResource known_move,
        NamedAPIResource known_move_type,
        NamedAPIResource location,
        int min_level,
        int min_happiness,
        int min_beauty,
        int min_affection,
        boolean needs_overworld_rain,
        NamedAPIResource party_species,
        NamedAPIResource party_type,
        int relative_physical_stats,
        String time_of_day,
        NamedAPIResource trade_species,
        boolean turn_upside_down
) {
}
