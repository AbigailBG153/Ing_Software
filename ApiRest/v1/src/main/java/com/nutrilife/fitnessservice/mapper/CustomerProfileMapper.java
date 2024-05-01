package com.nutrilife.fitnessservice.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerProfileMapper {
    
    private final ModelMapper modelMapper;

    public CustomerProfile convertToEntity(CustomerProfileRequestDTO customerProfileRequestDTO){
        return modelMapper.map(customerProfileRequestDTO, CustomerProfile.class);
    }

    public CustomerProfileResponseDTO convertToDTO(CustomerProfile customerProfile){
        return modelMapper.map(customerProfile, CustomerProfileResponseDTO.class);
    }

    public List<CustomerProfileResponseDTO> convertToListDTO(List<CustomerProfile> profiles){
        return profiles.stream()
            .map(this::convertToDTO)
            .toList();
    }
}
