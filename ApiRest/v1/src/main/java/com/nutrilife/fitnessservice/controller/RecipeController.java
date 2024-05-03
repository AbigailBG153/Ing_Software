package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
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
}