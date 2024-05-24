package com.nutrilife.fitnessservice.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.exception.ValidationUserRegisterException;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;
import com.nutrilife.fitnessservice.repository.UserRespository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${storage.location}")
    private String storageLocation;

    private Path rootLocation;

    private final SpecialistProfileRepository specialistProfileRepository;
    private final SpecialistProfileMapper specialistProfileMapper;
    private final UserRespository userRespository;

    @Autowired
    public FileSystemStorageService(SpecialistProfileRepository specialistProfileRepository, 
                                        SpecialistProfileMapper specialistProfileMapper, UserRespository userRespository){
        this.specialistProfileMapper = specialistProfileMapper;
        this.specialistProfileRepository = specialistProfileRepository;
        this.userRespository = userRespository;
    }

    @PostConstruct
    @Override
    public void init() {
        if (storageLocation.trim().isEmpty()) {
            throw new RuntimeException("File upload location can not be Empty.");
        }

        rootLocation = Paths.get(storageLocation);

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public SpecialistProfileResponseDTO store(MultipartFile file, String email) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            String originalFilename = file.getOriginalFilename();
            String filename = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFilename);

            Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            User user = userRespository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("No existe el usuario con el siguiente email"));

            SpecialistProfile specialist = specialistProfileRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new UserNotFound("No existe el usuario con el siguiente user_id"));
            
            specialist.setStudCertificate(filename);

            specialistProfileRepository.save(specialist);

            return specialistProfileMapper.convertToDTO(specialist);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ValidationUserRegisterException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ValidationUserRegisterException("Could not read file: " + filename);
        }
    }

    @Override
    public void delete(String filename) {
        Path file = load(filename);
        try {
            FileSystemUtils.deleteRecursively(file);
        } catch (IOException ex) {
            throw new RuntimeException("Cann't delete the file.");
        }
    }
}
