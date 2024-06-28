package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;
import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.service.IngredientService;
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
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponseDTO> updateIngredient(@PathVariable Long id,
            @RequestBody IngredientRequestDTO ingredientDTO) {
        IngredientResponseDTO updatedIngredient = ingredientService.updateIngredient(id, ingredientDTO);
        return ResponseEntity.ok(updatedIngredient);
    }

}