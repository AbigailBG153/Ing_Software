package com.nutrilife.fitnessservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrilife.fitnessservice.model.dto.TrainingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingUpdateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTraining() throws Exception {
        TrainingRequestDTO requestDTO = new TrainingRequestDTO();
        requestDTO.setName("Training 3");
        requestDTO.setDescription("Test training");
        requestDTO.setExerciseType("Cardio");
        requestDTO.setPhysicalGoal("Weight Loss");
        requestDTO.setDuration(60.0f);
        requestDTO.setKCalories(500.0f);
        requestDTO.setVideo("video_url");

        mockMvc.perform(MockMvcRequestBuilders.post("/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetTrainingById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/{id}", 2L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllTrainings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateTraining() throws Exception {
        TrainingUpdateDTO updateDTO = new TrainingUpdateDTO();
        updateDTO.setName("Updated Training");
        updateDTO.setDescription("Updated description");
        updateDTO.setExerciseType("Strength");
        updateDTO.setPhysicalGoal("Muscle Gain");
        updateDTO.setDuration(75.0f);
        updateDTO.setKCalories(600.0f);
        updateDTO.setVideo("updated_video_url");

        mockMvc.perform(MockMvcRequestBuilders.put("/trainings/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTraining() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/trainings/{id}", 3L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testGetTrainingsByExerciseType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/type/{type}", "Cardio"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGenerateTrainingReport() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/report")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-31"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
