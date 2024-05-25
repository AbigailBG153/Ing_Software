package com.nutrilife.fitnessservice.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SpecialistProfileMapper {
    
    private final ModelMapper modelMapper;

    public SpecialistProfile convertToEntity(SpecialistProfileRequestDTO specialistProfileRequestDTO){
        
        return modelMapper.map(specialistProfileRequestDTO, SpecialistProfile.class);
    }

    public UserRequestDTO createUserRequestDTO(SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(specialistProfileRequestDTO.getEmail());
        userRequestDTO.setPassword(specialistProfileRequestDTO.getPassword());
        return userRequestDTO;
    }

    public SpecialistProfileResponseDTO convertToDTO(SpecialistProfile specialistProfile){
        return modelMapper.map(specialistProfile, SpecialistProfileResponseDTO.class);
    }

    public List<SpecialistProfileResponseDTO> convertToListDTO(List<SpecialistProfile> profiles){
        return profiles.stream()
            .map(this::convertToDTO)
            .toList();
    }
    
}
