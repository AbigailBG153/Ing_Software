package com.nutrilife.fitnessservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecipeMapper {

    private final ModelMapper modelMapper;

    public Recipe convertToEntity(RecipeRequestDTO recipeRequestDTO) {
        return modelMapper.map(recipeRequestDTO, Recipe.class);
    }

    public RecipeResponseDTO convertToDTO(Recipe recipe) {
        RecipeResponseDTO recipeResponseDTO = modelMapper.map(recipe, RecipeResponseDTO.class);
        List<IngredientResponseDTO> ingredientResponseDTOs = recipe.getIngredients()
                .stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientResponseDTO.class))
                .collect(Collectors.toList());
        recipeResponseDTO.setIngredients(ingredientResponseDTOs);
        return recipeResponseDTO;
    }

    public List<RecipeResponseDTO> convertToListDTO(List<Recipe> recipes) {
        return recipes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
