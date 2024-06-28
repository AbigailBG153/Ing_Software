package com.nutrilife.fitnessservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String type;
    private String nutritionalGoal;
    private float totalCalories;
    private String image;
    private List<String> ingredients;
    private float score;

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}