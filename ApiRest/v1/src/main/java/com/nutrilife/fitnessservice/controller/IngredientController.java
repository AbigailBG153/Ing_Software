package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.mapper.RecipeMapper;
import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;
import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import com.nutrilife.fitnessservice.service.IngredientService;
import com.nutrilife.fitnessservice.service.RecipeService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;

    @GetMapping
    public ResponseEntity<List<IngredientResponseDTO>> getAllIngredients() {
        List<IngredientResponseDTO> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> getIngredientById(@PathVariable Long id) {
        IngredientResponseDTO ingredient = ingredientService.getIngredientById(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IngredientResponseDTO> createIngredient(
            @Validated @RequestBody IngredientRequestDTO ingredientDTO) {
        IngredientResponseDTO ingredient = ingredientService.createIngredient(ingredientDTO);
        return new ResponseEntity<>(ingredient, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {

        List<Recipe> recipes = recipeService.getRecipesByIngredients(List.of(id));
        List<RecipeResponseDTO> recipeDTOs = recipeService.convertToDTOListFromRecipe(recipes);

        for (RecipeResponseDTO recipe : recipeDTOs) {
            recipe.getIngredients().removeIf(ingredientId -> ingredientId.equals(id));
            recipeService.updateRecipe(recipe.getId(), recipeMapper.convertToResponseDTO(recipe));
        }

        ingredientService.deleteIngredient(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> updateIngredient(@PathVariable Long id,
            @RequestBody IngredientRequestDTO ingredientDTO) {
        IngredientResponseDTO updatedIngredient = ingredientService.updateIngredient(id, ingredientDTO);
        return ResponseEntity.ok(updatedIngredient);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<IngredientResponseDTO>> findIngredientsByName(@RequestParam String name) {
        List<IngredientResponseDTO> ingredients = ingredientService.findIngredientsByName(name);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }
}