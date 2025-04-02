package com.agz.pokeapi.model;

import java.util.List;

public record PokemonSpeciesDetail(
        int id,
        String name,
        int order,
        int gender_rate,
        int capture_rate,
        int base_happiness,
        boolean is_baby,
        boolean is_legendary,
        boolean is_mythical,
        int hatch_counter,
        boolean has_gender_differences,
        boolean forms_switchable,
        NamedAPIResource growth_rate,
        List<PokemonSpeciesDexEntry> pokedex_numbers,
        List<NamedAPIResource> egg_groups,
        NamedAPIResource color,
        NamedAPIResource shape,
        NamedAPIResource evolves_from_species,
        ApiResource evolution_chain,
        NamedAPIResource habitat,
        NamedAPIResource generation,
        List<Name> names,
        List<PalParkEncounterArea> pal_park_encounters,
        List<PokemonFlavorText> flavor_text_entries,
        List<Description> form_descriptions,
        List<Genus> genera,
        List<PokemonSpeciesVariety> varieties

) {
}
