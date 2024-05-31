package com.nutrilife.fitnessservice.service;

import com.nutrilife.fitnessservice.exception.IngredientNotFoundException;
import com.nutrilife.fitnessservice.mapper.IngredientMapper;
import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;
import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
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
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeRepository recipeRepository;

    @Transactional
    public IngredientResponseDTO createIngredient(IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = ingredientMapper.convertToEntity(ingredientRequestDTO);
        ingredientRepository.save(ingredient);
        return ingredientMapper.convertToDTO(ingredient);
    }

    @Transactional(readOnly = true)
    public IngredientResponseDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        return ingredientMapper.convertToDTO(ingredient);
    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(ingredientMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteIngredient(Long id) {
        // Obtener las recetas que contienen el ingrediente
        List<Recipe> recipes = recipeRepository.findByIngredients_IdIn(List.of(id));

        // Actualizar las recetas para quitar el ingrediente
        for (Recipe recipe : recipes) {
            recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(id));
            recipeRepository.save(recipe);
        }

        // Eliminar el ingrediente
        ingredientRepository.deleteById(id);
    }

    @Transactional
    public IngredientResponseDTO updateIngredient(Long id, IngredientRequestDTO ingredientRequestDTO) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        if (ingredientRequestDTO.getName() != null) {
            ingredient.setName(ingredientRequestDTO.getName());
        }
        if (ingredientRequestDTO.getCalories() >= 0) {
            ingredient.setCalories(ingredientRequestDTO.getCalories());
        }

        ingredient = ingredientRepository.save(ingredient);

        return ingredientMapper.convertToDTO(ingredient);

    }

    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> findIngredientsByName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameIgnoreCase(name);
        return ingredients.stream()
                .map(ingredientMapper::convertToDTO)
                .collect(Collectors.toList());
    }

}
