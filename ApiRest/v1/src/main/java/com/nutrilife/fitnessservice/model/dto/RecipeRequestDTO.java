package com.nutrilife.fitnessservice.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import com.nutrilife.fitnessservice.model.entity.Ingredient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "El tipo no puede estar vacío")
    private String type;

    @NotBlank(message = "El objetivo nutricional no puede estar vacío")
    private String nutritionalGoal;

    @PositiveOrZero(message = "El total de calorías no puede ser negativo")
    @Max(value = 3000, message = "El total de calorías no puede ser mayor a 3000")
    private float totalCalories;

    @NotBlank(message = "El URL de la imagen no puede estar vacía")
    private String image;

    @NotEmpty(message = "Los ingredientes no pueden estar vacíos")
    private List<Ingredient> ingredients;

    @NotNull(message = "El score no puede estar vacío")
    @Min(value = 0, message = "El score no puede ser un valor negativo")
    @Max(value = 5, message = "El score no puede ser mayor a 5")
    private float score;
}