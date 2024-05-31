package com.nutrilife.fitnessservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrilife.fitnessservice.model.dto.RecipeRequestDTO;
import com.nutrilife.fitnessservice.model.dto.RecipeResponseDTO;
import com.nutrilife.fitnessservice.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void testGetAllRecipes() throws Exception {
        // Arrange
        when(recipeService.getAllRecipes()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipeById() throws Exception {
        // Arrange
        Long recipeId = 1L;
        RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
        when(recipeService.getRecipeById(recipeId)).thenReturn(recipeResponseDTO);

        // Act & Assert
        mockMvc.perform(get("/recipes/{id}", recipeId))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateRecipe() throws Exception {
        // Arrange
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setName("Pollo a la parrilla");
        recipeRequestDTO.setDescription("Una deliciosa receta de pollo a la parrilla con hierbas frescas.");
        recipeRequestDTO.setType("Plato principal");
        recipeRequestDTO.setNutritionalGoal("Ganar masa muscular");
        recipeRequestDTO.setTotalCalories(250);
        recipeRequestDTO.setImage("https://example.com/images/grilled_chicken.jpg");
        recipeRequestDTO.setIngredientIds(List.of(1L, 2L));
        recipeRequestDTO.setScore(4.5f);

        // Act & Assert
        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        // Arrange
        Long recipeId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/recipes/{id}", recipeId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        // Arrange
        Long recipeId = 1L;
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setName("Pollo a la parrilla");
        recipeRequestDTO.setDescription("Una deliciosa receta de pollo a la parrilla con hierbas frescas.");
        recipeRequestDTO.setType("Plato principal");
        recipeRequestDTO.setNutritionalGoal("Ganar masa muscular");
        recipeRequestDTO.setTotalCalories(250);
        recipeRequestDTO.setImage("https://example.com/images/grilled_chicken.jpg");
        recipeRequestDTO.setIngredientIds(List.of(1L, 2L));
        recipeRequestDTO.setScore(4.5f);

        // Act & Assert
        mockMvc.perform(put("/recipes/{id}", recipeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByNutritionalGoal() throws Exception {
        // Arrange
        String nutritionalGoal = "Ganar masa muscular";

        // Act & Assert
        mockMvc.perform(get("/recipes/byNutritionalGoal/{nutritionalGoal}", nutritionalGoal))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByCaloriesRange() throws Exception {
        // Arrange
        float minCalories = 100.0f;
        float maxCalories = 500.0f;

        // Act & Assert
        mockMvc.perform(get("/recipes/calories")
                .param("minCalories", String.valueOf(minCalories))
                .param("maxCalories", String.valueOf(maxCalories)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTopRatedRecipes() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/recipes/top-rated"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByType() throws Exception {
        // Arrange
        String type = "vegano";

        // Act & Assert
        mockMvc.perform(get("/recipes/by-type/{type}", type))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByIngredients() throws Exception {
        // Arrange
        List<Long> ingredientIds = Arrays.asList(1L, 2L);

        // Act & Assert
        mockMvc.perform(get("/recipes/byIngredients")
                .param("ingredientIds", "1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPopularRecipesByIngredients() throws Exception {
        // Arrange
        List<Long> ingredientIds = Arrays.asList(1L, 2L);

        // Act & Assert
        mockMvc.perform(get("/recipes/popular/byIngredients")
                .param("ingredientIds", "1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByTypeAndNutritionalGoal() throws Exception {
        // Arrange
        String type = "vegano";
        String nutritionalGoal = "Ganar masa muscular";

        // Act & Assert
        mockMvc.perform(get("/recipes/byTypeAndNutritionalGoal")
                .param("type", type)
                .param("nutritionalGoal", nutritionalGoal))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecipesByNutritionalGoalAndIngredients() throws Exception {
        // Arrange
        String nutritionalGoal = "Ganar masa muscular";
        List<Long> ingredientIds = Arrays.asList(1L, 2L);

        // Act & Assert
        mockMvc.perform(get("/recipes/byNutritionalGoalAndIngredients")
                .param("nutritionalGoal", nutritionalGoal)
                .param("ingredientIds", "1,2"))
                .andExpect(status().isOk());
    }
}
