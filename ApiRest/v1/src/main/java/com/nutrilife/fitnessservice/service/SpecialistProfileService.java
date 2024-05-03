package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.UserNotFound;
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

    //Crear perfil del usuario especialista
    @Transactional
    public SpecialistProfileResponseDTO createProfileSpecialist(SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        SpecialistProfile specialistProfile = specialistProfileMapper.convertToEntity(specialistProfileRequestDTO);
        specialistProfileRepository.save(specialistProfile);
        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional(readOnly = true)
    public SpecialistProfileResponseDTO getSpecialistProfileByUserId(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findByUserId(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
        
        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
        public SpecialistProfileResponseDTO getSpecialistProfileById(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
            
        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
    public List<SpecialistProfileResponseDTO> getAllSpecialistProfile() {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findAll();
        return specialistProfileMapper.convertToListDTO(specialistProfiles);
    }

    @Transactional
    public SpecialistProfileResponseDTO updateSpecialistProfile(Long id, SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("Perfil de usuario no encontrado con el numero: "+id));
        
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
