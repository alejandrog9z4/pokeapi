package com.agz.pokeapi.service;

import com.agz.pokeapi.client.PokeApi;
import com.agz.pokeapi.exception.PokeApiException;
import com.agz.pokeapi.model.*;
import com.agz.pokeapi.utils.UtilClientPokeApi;
import com.agz.pokeapi.utils.UtilsPath;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "pokemonCache")
public class PokemonService implements IPokemonService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(PokemonService.class);


    @Autowired
    private PokeApi pokeApi;

    @Autowired
    private CacheManager cacheManager;

    @Value("${pokeapi.limit.default}")
    private int defaultLimit ;

    @Override
    @Cacheable(key = "'pokemon_' + #offset + '_' + #limit", unless = "#result == null")
    public PokemonAllFilteredResponse getAllPokemon(int offset, int limit) {


        if (limit != defaultLimit) {
            logger.info("Limit changed ({} != {}), clearing pokemonCache...", limit, defaultLimit);
            cacheManager.getCache("pokemonCache").clear();
        }

        PokemonAllResponse data = pokeApi.getPokemon(offset, limit);

        List<CompletableFuture<PokemonBasicInformationResponse>> futures =
                data.results().stream()
                        .map(pokemon -> CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(100); 
                                int id = Integer.parseInt(UtilsPath.ObtainId(pokemon.url()));
                                PokemonApiInfo pokemonApiInfo = pokeApi.getPokemonById(id);
                                logger.info("Pokemon: {}", pokemonApiInfo);
                                return new PokemonBasicInformationResponse(
                                        pokemonApiInfo.id(),
                                        pokemonApiInfo.name(),
                                        pokemonApiInfo.sprites().front_default(),
                                        pokemonApiInfo.weight(),
                                        UtilClientPokeApi.ObtainTypes(pokemonApiInfo.types()),
                                        UtilClientPokeApi.ObtainAbilities(pokemonApiInfo.abilities())
                                );
                            } catch (Exception e) {
                                logger.error("Error fetching Pokemon ID {}: {}", pokemon.url(), e.getMessage());
                                return null;
                            }
                        }))
                        .collect(Collectors.toList());
   
        List<PokemonBasicInformationResponse> results = futures.stream()
                .map(future -> {
                    try {
                        return future.get(15, TimeUnit.SECONDS); 
                    } catch (Exception e) {
                        logger.error("Future completion error: {}", e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new PokemonAllFilteredResponse(
                data.count(),
                UtilsPath.ObtainOffsetAndLimit(data.next()),
                UtilsPath.ObtainOffsetAndLimit(data.previous()),
                results
        );
    }

    @Override
    @Cacheable(value = "pokemonCache", key = "'pokemon_detail_' + #id")
    public PokemonDetailResponse getPokemonById(String id) {

        PokemonApiInfo pokemonApiInfo;

        if (UtilsPath.isNumber(id)) {
            int pokemonId = Integer.parseInt(id);
            pokemonApiInfo = pokeApi.getPokemonById(pokemonId);
        } else if (UtilsPath.isString(id)) {
            pokemonApiInfo = pokeApi.getPokemonByName(id);
        } else {
            throw new PokeApiException("Invalid ID or Name");
        }

        PokemonSpeciesDetail description = pokeApi.getPokemonSpecies(pokemonApiInfo.id());

        int idEvolution = Integer.parseInt(UtilsPath.ObtainId(description.evolution_chain().url()));

        EvolutionChain evolutions = pokeApi.getEvolutionChain(idEvolution);

        List<String> evolutionsList = UtilClientPokeApi.ObtainEvolutions(evolutions,pokemonApiInfo.name());



        return new PokemonDetailResponse(
                pokemonApiInfo.id(),
                pokemonApiInfo.name(),
                pokemonApiInfo.weight(),
                pokemonApiInfo.sprites().front_default(),
                UtilClientPokeApi.ObtainTypes(pokemonApiInfo.types()),
                UtilClientPokeApi.ObtainAbilities(pokemonApiInfo.abilities()),
                UtilClientPokeApi.ObtainDescription(description.flavor_text_entries()),
                evolutionsList
        );

    }

    @Scheduled(fixedRate = 3600000) 
    public void clearCache() {
        cacheManager.getCache("pokemonCache").clear();
    }

}