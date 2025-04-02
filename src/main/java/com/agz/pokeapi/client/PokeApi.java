package com.agz.pokeapi.client;

import com.agz.pokeapi.model.EvolutionChain;
import com.agz.pokeapi.model.PokemonAllResponse;

import com.agz.pokeapi.model.PokemonApiInfo;
import com.agz.pokeapi.model.PokemonSpeciesDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "pokeapi", url = "${pokeapi.base.url}")
public interface PokeApi {

    @GetMapping("/pokemon")
    PokemonAllResponse getPokemon(@RequestParam("offset") int offset, @RequestParam("limit") int limit);

    @GetMapping("/pokemon/{id}")
    PokemonApiInfo getPokemonById(@PathVariable int id);

    @GetMapping("/pokemon/{name}")
    PokemonApiInfo getPokemonByName(@PathVariable String name);

    @GetMapping("/pokemon-species/{id}")
    PokemonSpeciesDetail getPokemonSpecies(@PathVariable int id);

    @GetMapping("/evolution-chain/{id}")
    EvolutionChain getEvolutionChain(@PathVariable int id);
}
