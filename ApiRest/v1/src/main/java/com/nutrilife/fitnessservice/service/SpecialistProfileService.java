package com.nutrilife.fitnessservice.service;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.nutrilife.fitnessservice.exception.SpecialistNotFoundException;
import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;

import org.apache.commons.lang3.StringUtils;  
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialistProfileService {
    
    private final SpecialistProfileRepository specialistProfileRepository;
    private final SpecialistProfileMapper specialistProfileMapper;
    private final  UserService userService;
    
    //Crear perfil del usuario especialista
    @Transactional
    public SpecialistProfileResponseDTO createProfileSpecialist(SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        User user = userService.createUser(specialistProfileMapper.createUserRequestDTO(specialistProfileRequestDTO));
        
        SpecialistProfile specialistProfile = specialistProfileMapper.convertToEntity(specialistProfileRequestDTO);
        specialistProfile.setUser(user);
        
        // Capitalizar el nombre
        String capitalizedFullName = capitalizeName(specialistProfileRequestDTO.getName());
        specialistProfile.setName(capitalizedFullName);
        
        specialistProfile.setOccupation(specialistProfileRequestDTO.getOccupation().toUpperCase());
        // Asignación de otros campos como phoneNumber, age, score, etc.
        
        specialistProfileRepository.save(specialistProfile);
        
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);
        specDTO.setOccupation(specialistProfile.getOccupation());
        specDTO.setEmail(specialistProfile.getUser().getEmail());
        
        return specDTO;
    }
    
    // Método para capitalizar el nombre
    private String capitalizeName(String name) {
        StringBuilder result = new StringBuilder();
        String[] words = name.toLowerCase().split("\\s");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i > 0) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }

    @Transactional(readOnly = true)
    public SpecialistProfileResponseDTO getSpecialistProfileByUserId(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findByUserId(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
        
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);
        specDTO.setEmail(specialistProfile.getUser().getEmail());

        return specDTO;
    }

    @Transactional(readOnly = true)
        public SpecialistProfileResponseDTO getSpecialistProfileById(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
            
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);
        specDTO.setEmail(specialistProfile.getUser().getEmail());

        return specDTO;
    }

    @Transactional(readOnly = true)
    public List<SpecialistProfileResponseDTO> getAllSpecialistProfile() {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findAll();

        List<SpecialistProfileResponseDTO> specDTOs = specialistProfileMapper.convertToListDTO(specialistProfiles);
        List<String> emails = new ArrayList<>();
        specialistProfiles.forEach(profile ->{
            if(profile.getUser() != null){
                emails.add(profile.getUser().getEmail());
            }else{
                emails.add(null);
            }
        } );

        IntStream.range(0, specDTOs.size())
            .forEach(i -> specDTOs.get(i).setEmail(emails.get(i % emails.size())));

        return specDTOs;
    }

    @Transactional
    public SpecialistProfileResponseDTO updateSpecialistProfile(Long id, SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("Perfil de usuario no encontrado con el numero: "+id));
        
        specialistProfile.setAge(specialistProfileRequestDTO.getAge());
        specialistProfile.setName(specialistProfileRequestDTO.getName());
        specialistProfile.setOccupation(specialistProfileRequestDTO.getOccupation());
        specialistProfile.setPhoneNumber(specialistProfileRequestDTO.getPhoneNumber());
        //specialistProfile.setScore(specialistProfileRequestDTO.getScore());

        specialistProfile = specialistProfileRepository.save(specialistProfile);

        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByName(String name) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByName(name);
        if (specialistProfiles.isEmpty()) {
            throw new UserNotFound("Especialista no encontrado con el nombre: " + name);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }
    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByOccupation(String occupation) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByOccupation(occupation);
        if (specialistProfiles.isEmpty()) {
            throw new UserNotFound("Especialista no encontrado con la ocupación: " + occupation);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByAge(Integer age) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByAge(age);
        if (specialistProfiles.isEmpty()) {
            throw new UserNotFound("Especialista no encontrado con la edad: " + age);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByAgeRange(Integer minAge, Integer maxAge) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByAgeRange(minAge, maxAge);
        if (specialistProfiles.isEmpty()) {
            throw new UserNotFound("Especialistas no encontrados en el rango de edad: " + minAge + " to " + maxAge);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public void deleteSpecialistProfile(Long id) {
        specialistProfileRepository.deleteById(id);
    }



}
