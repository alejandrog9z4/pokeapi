package com.agz.pokeapi.model;

public record PokemonSprites(
        String back_default,
        String back_female,
        String back_shiny,
        String back_shiny_female,
        String front_default,
        String front_female,
        String front_shiny,
        String front_shiny_female
) {
}
