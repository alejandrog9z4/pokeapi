package com.agz.pokeapi.model;

import java.util.List;

public record PokemonAllResponse(int count,
                                 String next,
                                 String previous,
                                 List<NamedAPIResource> results) {
}
