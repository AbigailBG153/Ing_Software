package com.nutrilife.fitnessservice.service;

import com.nutrilife.fitnessservice.exception.RecipeNotFoundException;
import com.nutrilife.fitnessservice.mapper.RecipeMapper;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Ingredient;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import com.nutrilife.fitnessservice.repository.IngredientRepository;
import com.nutrilife.fitnessservice.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = recipeMapper.convertToEntity(recipeRequestDTO);
        List<Ingredient> ingredients = ingredientRepository.findAllById(recipeRequestDTO.getIngredientIds());
        recipe.setIngredients(ingredients);
        recipeRepository.save(recipe);
        return recipeMapper.convertToDTO(recipe);
    }

    @Transactional(readOnly = true)
    public RecipeResponseDTO getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        return recipeMapper.convertToDTO(recipe);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Transactional
    public RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        if (recipeRequestDTO.getName() != null) {
            recipe.setName(recipeRequestDTO.getName());
        }
        if (recipeRequestDTO.getDescription() != null) {
            recipe.setDescription(recipeRequestDTO.getDescription());
        }
        if (recipeRequestDTO.getType() != null) {
            recipe.setType(recipeRequestDTO.getType());
        }
        if (recipeRequestDTO.getNutritionalGoal() != null) {
            recipe.setNutritionalGoal(recipeRequestDTO.getNutritionalGoal());
        }
        if (recipeRequestDTO.getTotalCalories() >= 0 && recipeRequestDTO.getTotalCalories() <= 3000) {
            recipe.setTotalCalories(recipeRequestDTO.getTotalCalories());
        }
        if (recipeRequestDTO.getImage() != null) {
            recipe.setImage(recipeRequestDTO.getImage());
        }
        if (recipeRequestDTO.getIngredientIds() != null) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(recipeRequestDTO.getIngredientIds());
            recipe.setIngredients(ingredients);
        }
        if (recipeRequestDTO.getScore() >= 0 && recipeRequestDTO.getScore() <= 5) {
            recipe.setScore(recipeRequestDTO.getScore());
        }

        recipe = recipeRepository.save(recipe);

        return recipeMapper.convertToDTO(recipe);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> getRecipesByNutritionalGoal(String nutritionalGoal) {
        List<Recipe> recipes = recipeRepository.findByNutritionalGoal(nutritionalGoal);
        if (recipes.isEmpty()) {
            throw new RecipeNotFoundException("No recipes found for nutritional goal: " + nutritionalGoal);
        }
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> getRecipesByCaloriesRange(float minCalories, float maxCalories) {
        List<Recipe> recipes = recipeRepository.findByTotalCaloriesBetween(minCalories, maxCalories);
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeResponseDTO> getTopRatedRecipes() {
        List<Recipe> topRecipes = recipeRepository.findTop5ByOrderByScoreDesc();
        return topRecipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RecipeResponseDTO> getRecipesByType(String type) {
        List<Recipe> recipes = recipeRepository.findByType(type);
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Recipe> getRecipesByIngredients(List<Long> ingredientIds) {
        return recipeRepository.findByIngredients_IdIn(ingredientIds);
    }

    public List<RecipeResponseDTO> getPopularRecipesByIngredients(List<Long> ingredientIds) {
        List<Recipe> popularRecipes = recipeRepository.findPopularRecipesByIngredients(ingredientIds);
        List<Recipe> filteredRecipes = popularRecipes.stream()
                .filter(recipe -> recipe.getScore() >= 3.5f)
                .collect(Collectors.toList());
        return recipeMapper.convertToListDTO(filteredRecipes);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> getRecipesByTypeAndNutritionalGoal(String type, String nutritionalGoal) {
        List<Recipe> recipes = recipeRepository.findByTypeAndNutritionalGoal(type, nutritionalGoal);
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> getRecipesByNutritionalGoalAndIngredients(String nutritionalGoal,
            List<Long> ingredientIds) {
        List<Recipe> recipes = recipeRepository.findByNutritionalGoalAndIngredients(nutritionalGoal, ingredientIds);
        return recipes.stream()
                .map(recipeMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
