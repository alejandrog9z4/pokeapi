package com.agz.pokeapi.service;


import com.agz.pokeapi.model.PokemonAllFilteredResponse;
import com.agz.pokeapi.model.PokemonDetailResponse;


public interface IPokemonService {

    PokemonAllFilteredResponse getAllPokemon(int offset, int limit);

    PokemonDetailResponse getPokemonById(String id);



}
