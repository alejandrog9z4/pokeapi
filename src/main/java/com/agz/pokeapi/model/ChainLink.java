package com.agz.pokeapi.model;

import java.util.List;

public record ChainLink(
        boolean is_baby,
        NamedAPIResource species,
        List<EvolutionDetail> evolution_details,
        List<ChainLink> evolves_to
) {
}
