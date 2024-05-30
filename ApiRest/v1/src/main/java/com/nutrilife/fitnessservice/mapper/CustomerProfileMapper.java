package com.nutrilife.fitnessservice.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerProfileMapper {
    
    private final ModelMapper modelMapper;

    public CustomerProfile convertToEntity(CustomerProfileRequestDTO customerProfileRequestDTO){
        
        CustomerProfile customerProfile = modelMapper.map(customerProfileRequestDTO, CustomerProfile.class);

        //customerProfile.getUser().setEmail(customerProfileRequestDTO.getEmail());
        //customerProfile.getUser().setPassword(customerProfileRequestDTO.getPassword());
        return customerProfile;
    }

    public UserRequestDTO createUserRequestDTO(CustomerProfileRequestDTO customerProfileRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(customerProfileRequestDTO.getEmail());
        userRequestDTO.setPassword(customerProfileRequestDTO.getPassword());
        return userRequestDTO;
    }
    public CustomerProfileResponseDTO convertToDTO(CustomerProfile customerProfile){
        CustomerProfileResponseDTO customerProfileResponseDTO = modelMapper.map(customerProfile, CustomerProfileResponseDTO.class);
        //customerProfileResponseDTO.setEmail(customerProfile.getUser().getEmail());
        //customerProfileResponseDTO.setPassword(customerProfile.getUser().getPassword());
        return customerProfileResponseDTO;
    }

    public List<CustomerProfileResponseDTO> convertToListDTO(List<CustomerProfile> profiles){
        return profiles.stream()
            .map(this::convertToDTO)
            .toList();
    }

}
