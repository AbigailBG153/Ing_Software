package com.nutrilife.fitnessservice.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.dto.UserResponseDTO;
import com.nutrilife.fitnessservice.model.entity.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMapper {
    
    private final ModelMapper modelMapper;

    public User convertToEntity(UserRequestDTO userRequestDTO){
        return modelMapper.map(userRequestDTO, User.class);
    }

    public UserResponseDTO convertToDTO(User user){
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> convertToListDTO(List<User> users) {
        return users.stream()
            .map(this::convertToDTO)
            .toList();
    }
}
