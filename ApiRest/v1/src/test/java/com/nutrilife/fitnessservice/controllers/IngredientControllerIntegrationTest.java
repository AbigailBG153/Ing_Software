package com.nutrilife.fitnessservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrilife.fitnessservice.model.dto.IngredientRequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllIngredients() throws Exception {
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetIngredientById() throws Exception {
        Long id = 10L;
        mockMvc.perform(get("/ingredients/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateIngredient() throws Exception {
        IngredientRequestDTO ingredientRequestDTO = new IngredientRequestDTO();
        ingredientRequestDTO.setName("Ingredient Name");
        ingredientRequestDTO.setCalories(100);

        mockMvc.perform(post("/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ingredientRequestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/ingredients/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        Long id = 5L;
        IngredientRequestDTO ingredientRequestDTO = new IngredientRequestDTO();
        ingredientRequestDTO.setName("Updated Ingredient Name");
        ingredientRequestDTO.setCalories(150);

        mockMvc.perform(put("/ingredients/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ingredientRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindIngredientsByName() throws Exception {
        String name = "pimienta";
        mockMvc.perform(get("/ingredients/byName")
                .param("name", name))
                .andExpect(status().isOk());
    }

    // Método auxiliar para convertir objetos a JSON
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}