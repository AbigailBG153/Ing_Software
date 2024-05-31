package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.SpecialistNotFoundException;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialistProfileService {
    
    private final SpecialistProfileRepository specialistProfileRepository;
    private final SpecialistProfileMapper specialistProfileMapper;
  
    @Transactional
    public SpecialistProfileResponseDTO createProfileSpecialist(SpecialistProfileRequestDTO specialistProfileRequestDTO) {

        SpecialistProfile specialistProfile = specialistProfileMapper.convertToEntity(specialistProfileRequestDTO);
        specialistProfileRepository.save(specialistProfile);
        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
        public SpecialistProfileResponseDTO getSpecialistProfileById(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new SpecialistNotFoundException("Especialista no encontrado"));
            
        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getAllSpecialistProfile() {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findAll();
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByName(String name) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByName(name);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialista no encontrado con el nombre: " + name);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByOccupation(String occupation) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByOcuppation(occupation);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialista no encontrado con la ocupación: " + occupation);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByAge(Integer age) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByAge(age);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialista no encontrado con la edad: " + age);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByAgeRange(Integer minAge, Integer maxAge) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByAgeRange(minAge, maxAge);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialistas no encontrados en el rango de edad: " + minAge + " to " + maxAge);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByScore(Integer score) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByScore(score);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialista no encontrado con el puntaje: " + score);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getSpecialistProfilesByScoreRange(Integer minScore, Integer maxScore) {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findByScoreRange(minScore, maxScore);
        if (specialistProfiles.isEmpty()) {
            throw new SpecialistNotFoundException("Especialistas no encontrados en el rango de puntuación: " + minScore + " to " + maxScore);
        }
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }


    @Transactional
    public SpecialistProfileResponseDTO updateSpecialistProfile(Long id, SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new SpecialistNotFoundException("Perfil de especialista no encontrado con el id: "+id));
        
    if (specialistProfileRequestDTO.getName() != null) {
        specialistProfile.setName(specialistProfileRequestDTO.getName());
    }
    if (specialistProfileRequestDTO.getPhoneNumber() != null) {
        specialistProfile.setPhoneNumber(specialistProfileRequestDTO.getPhoneNumber());
    }
    if (specialistProfileRequestDTO.getScore() != null) {
        specialistProfile.setScore(specialistProfileRequestDTO.getScore());
    }

    specialistProfile = specialistProfileRepository.save(specialistProfile);

    return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
    public void deleteSpecialistProfile(Long id) {
        specialistProfileRepository.deleteById(id);
    }
}
