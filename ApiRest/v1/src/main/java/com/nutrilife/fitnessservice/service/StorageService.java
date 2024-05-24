package com.nutrilife.fitnessservice.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;

import java.nio.file.Path;


public interface StorageService {
    void init();

    SpecialistProfileResponseDTO store(MultipartFile file, String email);

    Path load(String filename);

    Resource loadAsResource(String filename);

    void delete(String filename);
}
