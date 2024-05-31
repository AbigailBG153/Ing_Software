package com.nutrilife.fitnessservice.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.model.entity.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomerProfileMapper {

    private final ModelMapper modelMapper;

    public CustomerProfile convertToEntity(CustomerProfileRequestDTO customerProfileRequestDTO, User user) {

        CustomerProfile customerProfile = modelMapper.map(customerProfileRequestDTO, CustomerProfile.class);
        customerProfile.setUser(user);

        return customerProfile;
    }

    public UserRequestDTO createUserRequestDTO(CustomerProfileRequestDTO customerProfileRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(customerProfileRequestDTO.getEmail());
        userRequestDTO.setPassword(customerProfileRequestDTO.getPassword());
        return userRequestDTO;
    }

    public CustomerProfileResponseDTO convertToDTO(CustomerProfile customerProfile) {
        CustomerProfileResponseDTO custDTO = modelMapper.map(customerProfile, CustomerProfileResponseDTO.class);
        custDTO.setEmail(customerProfile.getUser().getEmail());

        return custDTO;
    }

    public List<CustomerProfileResponseDTO> convertToListDTO(List<CustomerProfile> customerProfiles) {
        List<CustomerProfileResponseDTO> custDTOs = customerProfiles.stream()
                .map(this::convertToDTO)
                .toList();

        List<String> emails = new ArrayList<>();
        customerProfiles.forEach(profile -> emails.add(profile.getUser().getEmail()));

        IntStream.range(0, custDTOs.size())
                .forEach(i -> custDTOs.get(i).setEmail(emails.get(i % emails.size())));

        return custDTOs;
    }

}