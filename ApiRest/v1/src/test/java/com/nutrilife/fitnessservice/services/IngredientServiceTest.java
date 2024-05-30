package com.nutrilife.fitnessservice.services;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import com.nutrilife.fitnessservice.exception.IngredientNotFoundException;
import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;
import com.nutrilife.fitnessservice.model.dto.IngredientResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Ingredient;
import com.nutrilife.fitnessservice.repository.IngredientRepository;
import com.nutrilife.fitnessservice.service.IngredientService;
import com.nutrilife.fitnessservice.mapper.IngredientMapper;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient ingredient;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private IngredientRequestDTO requestDTO;
    private IngredientResponseDTO ingredientResponseDTO1;
    private IngredientResponseDTO ingredientResponseDTO2;

    @BeforeEach
    void setUp() {

        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Ingredient 1");
        ingredient.setCalories(100);

        ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Ingredient 1");
        ingredient1.setCalories(100);

        ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Ingredient 2");
        ingredient2.setCalories(200);

        ingredientResponseDTO1 = new IngredientResponseDTO();
        ingredientResponseDTO1.setId(1L);
        ingredientResponseDTO1.setName("Ingredient 1");
        ingredientResponseDTO1.setCalories(100);

        ingredientResponseDTO2 = new IngredientResponseDTO();
        ingredientResponseDTO2.setId(2L);
        ingredientResponseDTO2.setName("Ingredient 2");
        ingredientResponseDTO2.setCalories(200);

        requestDTO = new IngredientRequestDTO();
        requestDTO.setName("Updated Ingredient");
        requestDTO.setCalories(150);
    }

    @Test
    void createIngredient_Success() {
        // Arrange
        IngredientRequestDTO requestDTO = new IngredientRequestDTO();
        requestDTO.setName("Ingredient 1");
        requestDTO.setCalories(100);
        when(ingredientMapper.convertToEntity(requestDTO)).thenReturn(ingredient1);
        when(ingredientRepository.save(ingredient1)).thenReturn(ingredient1);
        when(ingredientMapper.convertToDTO(ingredient1)).thenReturn(ingredientResponseDTO1);

        // Act
        IngredientResponseDTO responseDTO = ingredientService.createIngredient(requestDTO);

        // Assert
        assertEquals("Ingredient 1", responseDTO.getName());
        assertEquals(100, responseDTO.getCalories());

        verify(ingredientMapper).convertToEntity(requestDTO);
        verify(ingredientRepository).save(ingredient1);
        verify(ingredientMapper).convertToDTO(ingredient1);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void getIngredientById_Success() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientMapper.convertToDTO(ingredient1)).thenReturn(ingredientResponseDTO1);

        // Act
        IngredientResponseDTO responseDTO = ingredientService.getIngredientById(1L);

        // Assert
        assertEquals("Ingredient 1", responseDTO.getName());
        assertEquals(100, responseDTO.getCalories());

        verify(ingredientRepository).findById(1L);
        verify(ingredientMapper).convertToDTO(ingredient1);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void getIngredientById_IngredientNotFound() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.getIngredientById(1L);
        });

        verify(ingredientRepository).findById(1L);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void getAllIngredients_Success() {
        // Arrange
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));
        when(ingredientMapper.convertToDTO(ingredient1)).thenReturn(ingredientResponseDTO1);
        when(ingredientMapper.convertToDTO(ingredient2)).thenReturn(ingredientResponseDTO2);

        // Act
        List<IngredientResponseDTO> responseDTOs = ingredientService.getAllIngredients();

        // Assert
        assertEquals(2, responseDTOs.size());
        assertEquals("Ingredient 1", responseDTOs.get(0).getName());
        assertEquals(100, responseDTOs.get(0).getCalories());
        assertEquals("Ingredient 2", responseDTOs.get(1).getName());
        assertEquals(200, responseDTOs.get(1).getCalories());

        verify(ingredientRepository).findAll();
        verify(ingredientMapper).convertToDTO(ingredient1);
        verify(ingredientMapper).convertToDTO(ingredient2);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void deleteIngredient_Success() {

        // Act
        ingredientService.deleteIngredient(1L);

        // Assert
        verify(ingredientRepository).deleteById(1L);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void updateIngredient_Success() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
        when(ingredientMapper.convertToDTO(ingredient)).thenReturn(new IngredientResponseDTO());

        // Act
        IngredientResponseDTO responseDTO = ingredientService.updateIngredient(1L, requestDTO);

        // Assert
        assertEquals("Updated Ingredient", ingredient.getName());
        assertEquals(150, ingredient.getCalories());

        verify(ingredientRepository).findById(1L);
        verify(ingredientRepository).save(ingredient);
        verify(ingredientMapper).convertToDTO(ingredient);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void updateIngredient_IngredientNotFound() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.updateIngredient(1L, requestDTO);
        });

        verify(ingredientRepository).findById(1L);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }

    @Test
    void findIngredientsByName_Success() {
        // Arrange
        when(ingredientRepository.findByNameIgnoreCase("Ingredient"))
                .thenReturn(Arrays.asList(ingredient1, ingredient2));
        when(ingredientMapper.convertToDTO(ingredient1)).thenReturn(ingredientResponseDTO1);
        when(ingredientMapper.convertToDTO(ingredient2)).thenReturn(ingredientResponseDTO2);

        // Act
        List<IngredientResponseDTO> responseDTOs = ingredientService.findIngredientsByName("Ingredient");

        // Assert
        assertEquals(2, responseDTOs.size());
        assertEquals("Ingredient 1", responseDTOs.get(0).getName());
        assertEquals(100, responseDTOs.get(0).getCalories());
        assertEquals("Ingredient 2", responseDTOs.get(1).getName());
        assertEquals(200, responseDTOs.get(1).getCalories());

        verify(ingredientRepository).findByNameIgnoreCase("Ingredient");
        verify(ingredientMapper).convertToDTO(ingredient1);
        verify(ingredientMapper).convertToDTO(ingredient2);
        verifyNoMoreInteractions(ingredientRepository, ingredientMapper);
    }
}
