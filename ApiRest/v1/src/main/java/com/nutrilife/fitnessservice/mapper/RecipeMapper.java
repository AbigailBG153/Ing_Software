package com.nutrilife.fitnessservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Ingredient;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecipeMapper {

        private final ModelMapper modelMapper;

        public Recipe convertToEntity(RecipeRequestDTO recipeRequestDTO) {
                Recipe recipe = modelMapper.map(recipeRequestDTO, Recipe.class);
                // Seteamos los ingredientes manualmente
                List<Long> ingredientIds = recipeRequestDTO.getIngredientIds();
                List<Ingredient> ingredients = ingredientIds.stream()
                                .map(id -> {
                                        Ingredient ingredient = new Ingredient();
                                        ingredient.setId(id);
                                        return ingredient;
                                })
                                .collect(Collectors.toList());
                recipe.setIngredients(ingredients);
                return recipe;
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

        public RecipeRequestDTO convertToResponseDTO(RecipeResponseDTO recipeResponseDTO) {
                RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
                recipeRequestDTO.setName(recipeResponseDTO.getName());
                recipeRequestDTO.setDescription(recipeResponseDTO.getDescription());
                recipeRequestDTO.setType(recipeResponseDTO.getType());
                recipeRequestDTO.setNutritionalGoal(recipeResponseDTO.getNutritionalGoal());
                recipeRequestDTO.setTotalCalories(recipeResponseDTO.getTotalCalories());
                recipeRequestDTO.setImage(recipeResponseDTO.getImage());

                // Mapeo de la lista de IngredientResponseDTO a una lista de IDs
                List<Long> ingredientIds = recipeResponseDTO.getIngredients().stream()
                                .map(IngredientResponseDTO::getId)
                                .collect(Collectors.toList());
                recipeRequestDTO.setIngredientIds(ingredientIds);

                recipeRequestDTO.setScore(recipeResponseDTO.getScore());
                // Asegúrate de mapear correctamente los demás campos según tu implementación
                return recipeRequestDTO;
        }

}
