package com.nutrilife.fitnessservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;
import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Ingredient;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientMapper {

    private final ModelMapper modelMapper;

    public Ingredient convertToEntity(IngredientRequestDTO ingredientRequestDTO) {
        return modelMapper.map(ingredientRequestDTO, Ingredient.class);
    }

    public IngredientResponseDTO convertToDTO(Ingredient ingredient) {
        return modelMapper.map(ingredient, IngredientResponseDTO.class);
    }

    public List<IngredientResponseDTO> convertToListDTO(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}