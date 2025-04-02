package com.agz.pokeapi.utils;

import com.agz.pokeapi.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtilClientPokeApi {

    public static List<String> ObtainAbilities(List<PokemonAbility> abilities){
        List<String> abilities_filtred = abilities.stream().map(
                pokemonAbility -> pokemonAbility.ability().name()
        ).collect(Collectors.toList());
        return abilities_filtred;
    }

    public static List<String> ObtainTypes(List<PokemonTypes> types) {
        List<String> types_filtered = types.stream().map(
                pokemonTypes -> pokemonTypes.type().name()
        ).collect(Collectors.toList());
        return types_filtered;
    }

    public static String ObtainDescription(List<PokemonFlavorText> flavorTexts) {
        StringBuffer description = new StringBuffer();
        for (PokemonFlavorText flavorText : flavorTexts) {
            if (flavorText.language().name().equals("en")) {
                description = description.append(flavorText.flavor_text().replace("\n"," "));
            }
        }
        return description.toString();
    }

    public static List<String> ObtainEvolutions(EvolutionChain response, String pokemonName) {
        List<String> evolutionNames = new ArrayList<>();
        if (response != null && response.chain() != null) {
            traverseChain(response.chain(), evolutionNames, pokemonName);
        }
        return evolutionNames;
    }

    private static void traverseChain(ChainLink node, List<String> result, String pokemonName ) {
        String currentName = node.species().name();
        if(!currentName.equalsIgnoreCase(pokemonName)) {
            result.add(node.species().name());
        }

        for (ChainLink next : node.evolves_to()) {
            traverseChain(next, result, pokemonName);
        }
    }
}
