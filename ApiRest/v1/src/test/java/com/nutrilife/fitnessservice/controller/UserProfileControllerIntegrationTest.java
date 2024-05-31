package com.nutrilife.fitnessservice.controller;

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
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void TestGetAllUsers() throws Exception {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("diego1@mail.com");
        requestDTO.setPassword("abcdef%12345");

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                        .andExpect(MockMvcResultMatchers.status().isOk());
                
    }
    
    @Test
    public void TestGetUserById() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }   


    @Test
    public void TestDeleteCustomerProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
