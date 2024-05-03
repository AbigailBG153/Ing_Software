package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.UserMapper;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.dto.UserResponseDTO;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.UserRespository;

import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    
    private final UserRespository userRespository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.convertToEntity(userRequestDTO);
        userRespository.save(user);
        return userMapper.convertToDTO(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRespository.findById(id)
            .orElseThrow(() -> new UserNotFound("Usuario no encontrado con el id: "+id));

        return userMapper.convertToDTO(user);
    }

    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRespository.findAll();
        return userMapper.convertToListDTO(users);
    } 

    @Transactional
    public void deleteUser(Long id) {
        userRespository.deleteById(id);
    }
}
