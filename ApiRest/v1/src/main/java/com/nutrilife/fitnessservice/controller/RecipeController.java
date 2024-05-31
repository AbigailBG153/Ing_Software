package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import com.nutrilife.fitnessservice.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipes() {
        List<RecipeResponseDTO> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> getRecipeById(@PathVariable Long id) {
        RecipeResponseDTO recipe = recipeService.getRecipeById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@Validated @RequestBody RecipeRequestDTO recipeDTO) {
        RecipeResponseDTO recipe = recipeService.createRecipe(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> updateRecipe(@PathVariable Long id,
            @RequestBody RecipeRequestDTO recipeDTO) {
        RecipeResponseDTO updatedRecipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(updatedRecipe);
    }

    @GetMapping("/byNutritionalGoal/{nutritionalGoal}")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByNutritionalGoal(@PathVariable String nutritionalGoal) {
        List<RecipeResponseDTO> recipes = recipeService.getRecipesByNutritionalGoal(nutritionalGoal);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/calories")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByCaloriesRange(
            @RequestParam float minCalories,
            @RequestParam float maxCalories) {
        List<RecipeResponseDTO> recipes = recipeService.getRecipesByCaloriesRange(minCalories, maxCalories);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<RecipeResponseDTO>> getTopRatedRecipes() {
        List<RecipeResponseDTO> topRatedRecipes = recipeService.getTopRatedRecipes();
        return ResponseEntity.ok(topRatedRecipes);
    }

    @GetMapping("/by-type/{type}")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByType(@PathVariable String type) {
        List<RecipeResponseDTO> recipes = recipeService.getRecipesByType(type);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/byIngredients")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByIngredients(@RequestParam List<Long> ingredientIds) {
        List<Recipe> recipes = recipeService.getRecipesByIngredients(ingredientIds);
        List<RecipeResponseDTO> recipeDTOs = recipeService.convertToDTOListFromRecipe(recipes);
        return new ResponseEntity<>(recipeDTOs, HttpStatus.OK);
    }

    @GetMapping("/popular/byIngredients")
    public ResponseEntity<List<RecipeResponseDTO>> getPopularRecipesByIngredients(
            @RequestParam List<Long> ingredientIds) {
        List<RecipeResponseDTO> popularRecipes = recipeService.getPopularRecipesByIngredients(ingredientIds);
        return new ResponseEntity<>(popularRecipes, HttpStatus.OK);
    }

    @GetMapping("/byTypeAndNutritionalGoal")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByTypeAndNutritionalGoal(
            @RequestParam String type,
            @RequestParam String nutritionalGoal) {
        List<RecipeResponseDTO> recipes = recipeService.getRecipesByTypeAndNutritionalGoal(type, nutritionalGoal);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/byNutritionalGoalAndIngredients")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByNutritionalGoalAndIngredients(
            @RequestParam String nutritionalGoal,
            @RequestParam List<Long> ingredientIds) {
        List<RecipeResponseDTO> recipes = recipeService.getRecipesByNutritionalGoalAndIngredients(nutritionalGoal,
                ingredientIds);
        return ResponseEntity.ok(recipes);
    }
}
