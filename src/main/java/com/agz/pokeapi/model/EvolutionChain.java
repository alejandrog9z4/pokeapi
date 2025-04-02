package com.agz.pokeapi.model;

public record EvolutionChain(
        int id,
        NamedAPIResource baby_trigger_item,
        ChainLink chain

) {
}
