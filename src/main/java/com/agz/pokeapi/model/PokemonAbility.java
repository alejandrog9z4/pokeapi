package com.agz.pokeapi.model;

public record PokemonAbility(
        boolean is_hidden,
        int slot,
        NamedAPIResource ability
) { }
