package com.nutrilife.fitnessservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialistProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getSpecialistProfileById_ExistingId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/specialists/{id}", "1L"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getSpecialistProfileById_SpecialistNotFound_ThrowsException() throws Exception {
        mockMvc.perform(get("/api/specialists/{id}", 999L))
               .andExpect(status().isNotFound());
    }

    @Test
    public void getSpecialistProfilesByName_ExistingName() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?name=Juan Pérez")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json("[{\"specId\":1,\"name\":\"Juan Pérez\"},{\"specId\":2,\"name\":\"Juan Pérez\"}]"));
    }

    @Test
    public void getSpecialistProfilesByName_NameNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?name=Nombre no existente")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void getSpecialistProfilesByOccupation_ExistingOccupation() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?occupation=Nutricionista")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json("[{\"specId\":1,\"occupation\":\"Nutricionista\"},{\"specId\":2,\"occupation\":\"Nutricionista\"}]"));
    }

    @Test
    public void getSpecialistProfilesByOccupation_OccupationNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?occupation=Ocupacion no existente")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void getSpecialistProfilesByAge_ExistingAge() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?age=35")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json("[{\"specId\":1,\"age\":35},{\"specId\":2,\"age\":35}]"));
    }

    @Test
    public void getSpecialistProfilesByAge_AgeNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?age=45")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void getSpecialistProfilesByAgeRange_FoundSpecialists() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?minAge=30&maxAge=40")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json("[{\"specId\":1,\"age\":35},{\"specId\":2,\"age\":38}]"));
    }

    @Test
    public void getSpecialistProfilesByAgeRange_NoSpecialistsFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?minAge=40&maxAge=50")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }

    @Test
    public void getSpecialistProfilesByScore_ExistingScore() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?score=4")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json("[{\"specId\":1,\"score\":4},{\"specId\":2,\"score\":4}]"));
    }

    @Test
    public void getSpecialistProfilesByScore_ScoreNotFound() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(get("/specialists?score=3")
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isNotFound());
    }
}
