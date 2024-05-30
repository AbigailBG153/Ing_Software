package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;


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
        
        SpecialistProfile specialistProfile = specialistProfileMapper.convertToEntity(specialistProfileRequestDTO,user);
        

        specialistProfileRepository.save(specialistProfile);
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);

        return specDTO;
    }

    @Transactional(readOnly = true)
    public SpecialistProfileResponseDTO getSpecialistProfileByUserId(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findByUserId(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
        
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);

        return specDTO;
    }

    @Transactional(readOnly = true)
        public SpecialistProfileResponseDTO getSpecialistProfileById(Long id) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));
            
        SpecialistProfileResponseDTO specDTO = specialistProfileMapper.convertToDTO(specialistProfile);

        return specDTO;
    }

    @Transactional(readOnly = true)
    public List<SpecialistProfileResponseDTO> getAllSpecialistProfile() {
        List<SpecialistProfile> specialistProfiles = specialistProfileRepository.findAll();

        List<SpecialistProfileResponseDTO> specDTOs = specialistProfileMapper.convertToListDTO(specialistProfiles);

        return specDTOs;
    }

    @Transactional
    public SpecialistProfileResponseDTO updateSpecialistProfile(Long id, SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("Perfil de usuario no encontrado con el numero: "+id));
        
        specialistProfile.setAge(specialistProfileRequestDTO.getAge());
        specialistProfile.setName(specialistProfileRequestDTO.getName());
        specialistProfile.setOcupation(specialistProfileRequestDTO.getOcupation());
        specialistProfile.setPhoneNumber(specialistProfileRequestDTO.getPhoneNumber());
        specialistProfile.setScore(specialistProfileRequestDTO.getScore());

        specialistProfile = specialistProfileRepository.save(specialistProfile);

        return specialistProfileMapper.convertToDTO(specialistProfile);
    }

    @Transactional
    public void deleteSpecialistProfile(Long id) {
        specialistProfileRepository.deleteById(id);
    }
}
