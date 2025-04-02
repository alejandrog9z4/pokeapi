package com.agz.pokeapi.model;

public record PalParkEncounterArea(
        int base_score,
        int rate,
        NamedAPIResource area
) {
}
