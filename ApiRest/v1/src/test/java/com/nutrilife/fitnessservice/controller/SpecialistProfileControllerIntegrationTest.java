package com.nutrilife.fitnessservice.controller;


import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.service.SpecialistProfileService;
import com.nutrilife.fitnessservice.service.StorageService;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialistProfileControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpecialistProfileService specialistProfileService;

    @MockBean
    private StorageService storageService;

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
    @Test
    public void shouldSaveUploadedFile() throws Exception {
        String email = "Hilda@mail.com";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setEmail(email);

        given(this.storageService.store(multipartFile, email)).willReturn(responseDTO);

        this.mockMvc.perform(multipart("/specialists/upload")
                .file(multipartFile)
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));

        then(this.storageService).should().store(multipartFile, email);
    }
    
    @Test
    public void testGetResource() throws Exception {
        String filename = "test.txt";
        Path path = Files.createTempFile(filename, ".txt");
        File file = path.toFile();
        Files.write(path, "Test content".getBytes());

        Resource resource = new FileSystemResource(file);
        String contentType = Files.probeContentType(path);

        given(storageService.loadAsResource(filename)).willReturn(resource);

        this.mockMvc.perform(get("/specialists/download/{filename}", filename))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, contentType))
                .andExpect(content().bytes(Files.readAllBytes(path)));

        then(storageService).should().loadAsResource(filename);
    }
}
