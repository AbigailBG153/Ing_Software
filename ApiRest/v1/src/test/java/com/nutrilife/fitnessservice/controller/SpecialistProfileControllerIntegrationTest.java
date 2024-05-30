package com.nutrilife.fitnessservice.controller;


import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.service.SpecialistProfileService;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialistProfileControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialistProfileService specialistProfileService;

    @Test
    public void testGetAllProfiles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/specialists"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testgetSpecialistProfileById() throws Exception {
        Long id = 1L;

        //SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        // Initialize responseDTO with test data

        //given(specialistProfileService.getSpecialistProfileById(id)).willReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/specialists/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateSpecialistProfile() throws Exception {
        SpecialistProfileRequestDTO requestDTO = new SpecialistProfileRequestDTO();

        requestDTO.setAge(25);
        requestDTO.setEmail("test@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setOcupation("Specialist");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("12345678910");
        requestDTO.setScore(4);

        mockMvc.perform(MockMvcRequestBuilders.post("/specialists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeleteSpecialistProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/specialists/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
