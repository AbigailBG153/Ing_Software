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
import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.service.CustomerProfileService;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerProfileControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerProfileService customerProfileService;

    @Test
    public void TestCreateCutomerProfile() throws Exception {
        CustomerProfileRequestDTO requestDTO = new CustomerProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setAlergies("peanut alergie");
        requestDTO.setDietRestriction("peanut");
        requestDTO.setDietType("vegetarian");
        requestDTO.setHeight(1.75F);
        requestDTO.setWeight(65.8F);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }
    
    @Test
    public void TestGetAllProfiles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestGetCustomerProfileById() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestGetCustomerProfileByName() throws Exception {
        String name = "User Test";

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/name/{name}", name))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestFindByDietType() throws Exception {
        String dietType = "vegetarian";

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/dietType/{dietType}", dietType))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void TestDeleteCustomerProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", 1L))
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
