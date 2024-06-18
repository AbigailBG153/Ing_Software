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
        requestDTO.setName("Training 6");
        requestDTO.setDescription("Test training 3");
        requestDTO.setExerciseType("Cardio");
        requestDTO.setPhysicalGoal("Weight Loss");
        requestDTO.setDuration(60.0f);
        requestDTO.setKCalories(500.0f);
        requestDTO.setVideo("video_url");
        requestDTO.setQualification(4);

        mockMvc.perform(MockMvcRequestBuilders.post("/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetTrainingById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/{id}", 11L))
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
        updateDTO.setQualification(5);

        mockMvc.perform(MockMvcRequestBuilders.put("/trainings/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteTraining() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/trainings/{id}", 11L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testGetTrainingsByExerciseType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/type/{type}", "Cardio"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTrainingsByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/name/{name}", "Training 1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTrainingsByPhysicalGoal() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/goal/{goal}", "Weight Loss"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTrainingsByDuration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/duration/{duration}", 60.0f))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetTrainingsByKCalories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trainings/kcalories/{kCalories}", 500.0f))
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
