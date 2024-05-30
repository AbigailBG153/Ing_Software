package com.nutrilife.fitnessservice.services;

import com.nutrilife.fitnessservice.exception.IngredientNotFoundException;
import com.nutrilife.fitnessservice.exception.RecipeNotFoundException;
import com.nutrilife.fitnessservice.mapper.RecipeMapper;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Ingredient;
import com.nutrilife.fitnessservice.model.entity.Recipe;
import com.nutrilife.fitnessservice.repository.IngredientRepository;
import com.nutrilife.fitnessservice.repository.RecipeRepository;
import com.nutrilife.fitnessservice.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe;
    private Recipe recipe1;
    private Recipe recipe2;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    private RecipeRequestDTO requestDTO;
    private RecipeResponseDTO recipeResponseDTO1;
    private RecipeResponseDTO recipeResponseDTO2;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        requestDTO = new RecipeRequestDTO();
        requestDTO.setName("Pollo a la parrilla");
        requestDTO.setDescription("Una deliciosa receta de pollo a la parrilla con hierbas frescas.");
        requestDTO.setType("Plato principal");
        requestDTO.setNutritionalGoal("Ganar masa muscular");
        requestDTO.setTotalCalories(250);
        requestDTO.setImage("https://example.com/images/grilled_chicken.jpg");
        requestDTO.setIngredientIds(List.of(1L, 2L));
        requestDTO.setScore(4.5f);

        ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setName("Pollo");

        ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("Pimienta");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName(requestDTO.getName());
        recipe.setDescription(requestDTO.getDescription());
        recipe.setType(requestDTO.getType());
        recipe.setNutritionalGoal(requestDTO.getNutritionalGoal());
        recipe.setTotalCalories(requestDTO.getTotalCalories());
        recipe.setImage(requestDTO.getImage());
        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));
        recipe.setScore(4.5f);

        recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.setName("Recipe 1");
        recipe1.setTotalCalories(400);

        recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Recipe 2");
        recipe2.setTotalCalories(600);

        recipeResponseDTO1 = new RecipeResponseDTO();
        recipeResponseDTO1.setId(1L);
        recipeResponseDTO1.setName("Recipe 1");

        recipeResponseDTO2 = new RecipeResponseDTO();
        recipeResponseDTO2.setId(2L);
        recipeResponseDTO2.setName("Recipe 2");

        when(recipeRepository.findByTotalCaloriesBetween(anyFloat(), anyFloat()))
                .thenReturn(Arrays.asList(recipe1, recipe2));
    }

    @Test
    public void testCreateRecipe() {

        // Mock de IngredientRepository
        when(ingredientRepository.findAllById(requestDTO.getIngredientIds()))
                .thenReturn(Arrays.asList(ingredient1, ingredient2));

        // Mock de RecipeMapper
        when(recipeMapper.convertToEntity(requestDTO)).thenReturn(recipe);
        when(recipeMapper.convertToDTO(recipe)).thenReturn(new RecipeResponseDTO());

        // Mock de RecipeRepository
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // Act
        RecipeResponseDTO result = recipeService.createRecipe(requestDTO);

        // Assert
        assertNotNull(result);
        // Agregar más aserciones según sea necesario
    }

    @Test
    public void testGetRecipeById_ExistingId() {
        // Arrange
        Long id = 1L;
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
        when(recipeMapper.convertToDTO(recipe)).thenReturn(new RecipeResponseDTO());

        // Act
        RecipeResponseDTO responseDTO = recipeService.getRecipeById(id);

        // Assert
        assertNotNull(responseDTO);
        verify(recipeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetRecipeById_NonExistingId() {
        // Arrange
        Long id = 999L;
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipeById(id));
        verify(recipeRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAllRecipes() {
        // Arrange
        List<Recipe> recipeList = new ArrayList<>();
        when(recipeRepository.findAll()).thenReturn(recipeList);
        when(recipeMapper.convertToDTO(any())).thenReturn(new RecipeResponseDTO());

        // Act
        List<RecipeResponseDTO> responseDTOList = recipeService.getAllRecipes();

        // Assert
        assertNotNull(responseDTOList);
        assertEquals(recipeList.size(), responseDTOList.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteRecipe() {
        // Arrange
        Long id = 1L;

        // Act
        assertDoesNotThrow(() -> recipeService.deleteRecipe(id));

        // Assert
        verify(recipeRepository, times(1)).deleteById(id);
    }

    @Test
    void updateRecipe_Success() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(new Ingredient()));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.convertToDTO(any(Recipe.class))).thenReturn(new RecipeResponseDTO());

        // Act
        RecipeResponseDTO response = recipeService.updateRecipe(1L, requestDTO);

        // Assert
        verify(recipeRepository).findById(1L);
        verify(ingredientRepository, times(2)).findById(anyLong());
        verify(recipeRepository).save(recipe);
        verify(recipeMapper).convertToDTO(recipe);
    }

    @Test
    void updateRecipe_RecipeNotFound() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.updateRecipe(1L, requestDTO);
        });
    }

    @Test
    void updateRecipe_IngredientNotFound() {

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IngredientNotFoundException.class, () -> {
            recipeService.updateRecipe(1L, requestDTO);
        });
    }

    @Test
    void getRecipesByNutritionalGoal_Success() {

        // Arrange
        when(recipeRepository.findByNutritionalGoal(anyString())).thenReturn(Arrays.asList(recipe1, recipe2));
        when(recipeMapper.convertToDTO(recipe1)).thenReturn(recipeResponseDTO1);
        when(recipeMapper.convertToDTO(recipe2)).thenReturn(recipeResponseDTO2);

        // Act
        List<RecipeResponseDTO> response = recipeService.getRecipesByNutritionalGoal("Perder peso");

        // Assert
        assertEquals(2, response.size());
        assertEquals("Recipe 1", response.get(0).getName());
        assertEquals("Recipe 2", response.get(1).getName());

        verify(recipeRepository).findByNutritionalGoal("Perder peso");
        verify(recipeMapper).convertToDTO(recipe1);
        verify(recipeMapper).convertToDTO(recipe2);
    }

    @Test
    void getRecipesByNutritionalGoal_NoRecipesFound() {
        // Arrange
        when(recipeRepository.findByNutritionalGoal(anyString())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipesByNutritionalGoal("Perder peso");
        });

        verify(recipeRepository).findByNutritionalGoal("Perder peso");
        verify(recipeMapper, never()).convertToDTO(any());
    }

    @Test
    void getRecipesByCaloriesRange_Success() {
        // Arrange
        when(recipeRepository.findByTotalCaloriesBetween(anyFloat(), anyFloat()))
                .thenReturn(Arrays.asList(recipe1, recipe2));
        when(recipeMapper.convertToDTO(recipe1)).thenReturn(recipeResponseDTO1);
        when(recipeMapper.convertToDTO(recipe2)).thenReturn(recipeResponseDTO2);

        // Act
        List<RecipeResponseDTO> response = recipeService.getRecipesByCaloriesRange(300f, 700f);

        // Assert
        assertEquals(2, response.size());
        assertEquals("Recipe 1", response.get(0).getName());
        assertEquals("Recipe 2", response.get(1).getName());

        verify(recipeRepository).findByTotalCaloriesBetween(300f, 700f);
        verify(recipeMapper).convertToDTO(recipe1);
        verify(recipeMapper).convertToDTO(recipe2);
    }

    @Test
    void getRecipesByCaloriesRange_NoRecipesFound() {
        // Arrange
        when(recipeRepository.findByTotalCaloriesBetween(anyFloat(), anyFloat())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipesByCaloriesRange(300f, 700f);
        });

        verify(recipeRepository).findByTotalCaloriesBetween(300f, 700f);
        verify(recipeMapper, never()).convertToDTO(any());
    }

    @Test
    void getTopRatedRecipes_Success() {
        // Arrange
        List<Recipe> topRecipes = Arrays.asList(recipe1, recipe2);
        when(recipeRepository.findTop5ByOrderByScoreDesc()).thenReturn(topRecipes);
        when(recipeMapper.convertToDTO(recipe1)).thenReturn(recipeResponseDTO1);
        when(recipeMapper.convertToDTO(recipe2)).thenReturn(recipeResponseDTO2);

        // Act
        List<RecipeResponseDTO> response = recipeService.getTopRatedRecipes();

        // Assert
        assertEquals(2, response.size());
        assertEquals("Recipe 1", response.get(0).getName());
        assertEquals("Recipe 2", response.get(1).getName());

        verify(recipeRepository).findTop5ByOrderByScoreDesc();
        verify(recipeMapper).convertToDTO(recipe1);
        verify(recipeMapper).convertToDTO(recipe2);
    }

    @Test
    void getRecipesByType_Success() {
        // Arrange
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);
        when(recipeRepository.findByType("Main Course")).thenReturn(Arrays.asList(recipe1));
        when(recipeMapper.convertToDTO(recipe1)).thenReturn(recipeResponseDTO1);

        // Act
        List<RecipeResponseDTO> response = recipeService.getRecipesByType("Main Course");

        // Assert
        assertEquals(1, response.size());
        assertEquals("Recipe 1", response.get(0).getName());

        verify(recipeRepository).findByType("Main Course");
        verify(recipeMapper).convertToDTO(recipe1);
        verifyNoMoreInteractions(recipeRepository, recipeMapper);
    }

    @Test
    void getRecipesByIngredients_Success() {
        // Arrange
        List<Long> ingredientIds = Arrays.asList(1L, 2L);
        when(recipeRepository.findByIngredients_IdIn(ingredientIds)).thenReturn(Arrays.asList(recipe1, recipe2));

        // Act
        List<Recipe> recipes = recipeService.getRecipesByIngredients(ingredientIds);

        // Assert
        assertEquals(2, recipes.size());
        assertEquals("Recipe 1", recipes.get(0).getName());
        assertEquals("Recipe 2", recipes.get(1).getName());

        verify(recipeRepository).findByIngredients_IdIn(ingredientIds);
        verifyNoMoreInteractions(recipeRepository);
    }

    @Test
    void getPopularRecipesByIngredients_Success() {
        // Arrange
        List<Long> ingredientIds = Arrays.asList(1L, 2L);
        List<Recipe> popularRecipes = Arrays.asList(recipe1, recipe2);
        when(recipeRepository.findPopularRecipesByIngredients(ingredientIds)).thenReturn(popularRecipes);
        when(recipeMapper.convertToListDTO(
                popularRecipes.stream().filter(recipe -> recipe.getScore() >= 3.5f).collect(Collectors.toList())))
                .thenReturn(Arrays.asList(recipeResponseDTO1));

        // Act
        List<RecipeResponseDTO> response = recipeService.getPopularRecipesByIngredients(ingredientIds);

        // Assert
        assertEquals(1, response.size());
        assertEquals("Recipe 1", response.get(0).getName());

        verify(recipeRepository).findPopularRecipesByIngredients(ingredientIds);
        verify(recipeMapper).convertToListDTO(
                popularRecipes.stream().filter(recipe -> recipe.getScore() >= 3.5f).collect(Collectors.toList()));
        verifyNoMoreInteractions(recipeRepository, recipeMapper);
    }

    @Test
    void getRecipesByTypeAndNutritionalGoal_Success() {
        // Arrange
        when(recipeRepository.findByTypeAndNutritionalGoal("vegano", "Perder peso")).thenReturn(Arrays.asList(recipe1));
        when(recipeMapper.convertToDTO(recipe1)).thenReturn(recipeResponseDTO1);

        // Act
        List<RecipeResponseDTO> response = recipeService.getRecipesByTypeAndNutritionalGoal("vegano", "Perder peso");

        // Assert
        assertEquals(1, response.size());
        assertEquals("Recipe 1", response.get(0).getName());

        verify(recipeRepository).findByTypeAndNutritionalGoal("vegano", "Perder peso");
        verify(recipeMapper).convertToDTO(recipe1);
        verifyNoMoreInteractions(recipeRepository, recipeMapper);
    }

    @Test
    void getRecipesByNutritionalGoalAndIngredients_Success() {
        // Arrange
        List<Long> ingredientIds = Arrays.asList(1L, 2L);
        when(recipeRepository.findByNutritionalGoalAndIngredients("Ganar masa muscular", ingredientIds))
                .thenReturn(Arrays.asList(recipe));
        when(recipeMapper.convertToDTO(recipe)).thenReturn(recipeResponseDTO1);

        // Act
        List<RecipeResponseDTO> response = recipeService.getRecipesByNutritionalGoalAndIngredients(
                "Ganar masa muscular",
                ingredientIds);

        // Assert
        assertEquals(1, response.size());
        assertEquals("Recipe 1", response.get(0).getName());

        verify(recipeRepository).findByNutritionalGoalAndIngredients("Ganar masa muscular", ingredientIds);
        verify(recipeMapper).convertToDTO(recipe);
        verifyNoMoreInteractions(recipeRepository, recipeMapper);
    }
}
