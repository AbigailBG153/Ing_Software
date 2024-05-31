package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.exception.ValidationUserRegisterException;
import com.nutrilife.fitnessservice.mapper.UserMapper;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.dto.UserResponseDTO;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.UserRespository;

import jakarta.validation.Valid;

//import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Validated
public class UserService {

    private final UserRespository userRespository;
    private final UserMapper userMapper;

    @Transactional
    public User createUser(@Validated UserRequestDTO userRequestDTO) {
        if (userRespository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ValidationUserRegisterException("El email ya está registrado");
        }

        User user = userMapper.convertToEntity(userRequestDTO);
        userRespository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRespository.findById(id)
                .orElseThrow(() -> new UserNotFound("Usuario no encontrado con el id: " + id));

        return userMapper.convertToDTO(user);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRespository.findById(id)
                .orElseThrow(() -> new UserNotFound("Usuario no encontrado con el id: " + id));

        if (!existingUser.getEmail().equals(userRequestDTO.getEmail())
                && userRespository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ValidationUserRegisterException("El nuevo email ya está registrado");
        }

        existingUser.setEmail(userRequestDTO.getEmail());
        existingUser.setPassword(userRequestDTO.getPassword());

        User updatedUser = userRespository.save(existingUser);
        return userMapper.convertToDTO(updatedUser);
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
