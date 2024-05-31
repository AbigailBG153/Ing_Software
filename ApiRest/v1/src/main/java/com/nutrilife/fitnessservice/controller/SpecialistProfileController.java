package com.nutrilife.fitnessservice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.service.SpecialistProfileService;
import com.nutrilife.fitnessservice.service.StorageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/specialists")
@AllArgsConstructor
public class SpecialistProfileController {

    private final SpecialistProfileService specialistProfileService;
    private final StorageService storageService;

    @GetMapping
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getAllProfiles() {
        List<SpecialistProfileResponseDTO> specialists = specialistProfileService.getAllSpecialistProfile();
        return new ResponseEntity<>(specialists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistProfileResponseDTO> getSpecialistProfileById(@PathVariable Long id) {
        SpecialistProfileResponseDTO specialist = specialistProfileService.getSpecialistProfileById(id);
        return new ResponseEntity<>(specialist, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<SpecialistProfileResponseDTO> upload(@RequestParam("file") MultipartFile multipartFile,
            @RequestParam("email") String email) {
        SpecialistProfileResponseDTO specialist = storageService.store(multipartFile, email);
        return new ResponseEntity<>(specialist, HttpStatus.OK);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> getResource(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);

    @GetMapping("/{name}")
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getSpecialistProfilesByName(@RequestParam String name) {
        List<SpecialistProfileResponseDTO> specialists = specialistProfileService.getSpecialistProfilesByName(name);
        if (specialists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(specialists);
    }

    @GetMapping("/{occupation}")
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getSpecialistProfilesByOccupation(
            @RequestParam String occupation) {
        List<SpecialistProfileResponseDTO> specialists = specialistProfileService
                .getSpecialistProfilesByName(occupation);
        if (specialists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(specialists);
    }

    @GetMapping("/{age}")
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getSpecialistProfilesByAge(@RequestParam Integer age) {
        List<SpecialistProfileResponseDTO> specialists = specialistProfileService.getSpecialistProfilesByAge(age);
        if (specialists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(specialists);
    }

    @GetMapping("/{minAge, maxAge}")
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getSpecialistProfileByAgeRange(
            @RequestParam(required = false) Integer minAge, @RequestParam(required = false) Integer maxAge) {

        if (minAge != null && maxAge != null) {
            List<SpecialistProfileResponseDTO> specialists = specialistProfileService
                    .getSpecialistProfilesByAgeRange(minAge, maxAge);
            if (specialists.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(specialists);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{score}")
    public ResponseEntity<List<SpecialistProfileResponseDTO>> getSpecialistProfilesByScore(
            @RequestParam Integer score) {
        List<SpecialistProfileResponseDTO> specialists = specialistProfileService.getSpecialistProfilesByScore(score);
        if (specialists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(specialists);
    }

    @PostMapping
    public ResponseEntity<SpecialistProfileResponseDTO> createSpecialistProfile(
            @Validated @RequestBody SpecialistProfileRequestDTO specialistDTO) {
        SpecialistProfileResponseDTO createdSpecialistProfile = specialistProfileService
                .createProfileSpecialist(specialistDTO);
        return new ResponseEntity<>(createdSpecialistProfile, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialistProfileResponseDTO> updateSpecialistProfile(@PathVariable Long id,
            @RequestBody SpecialistProfileRequestDTO specialistDTO) {
        SpecialistProfileResponseDTO updateSpecialistProfile = specialistProfileService.updateSpecialistProfile(id,
                specialistDTO);
        return ResponseEntity.ok(updateSpecialistProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SpecialistProfileResponseDTO> deleteSpecialistProfile(@PathVariable Long id) {
        specialistProfileService.deleteSpecialistProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
