package com.nutrilife.fitnessservice.service;

import com.nutrilife.fitnessservice.exception.RecipeNotFoundException;
import com.nutrilife.fitnessservice.mapper.RecipeMapper;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Recipe;
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

    @Transactional
    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = recipeMapper.convertToEntity(recipeRequestDTO);
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
}
