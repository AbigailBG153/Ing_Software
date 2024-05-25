package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.CustomerProfileMapper;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserResponseDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.CustomerProfileRepository;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerProfileService {
    
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileMapper customerProfileMapper;
    private final  UserService userService;

    //Crear perfil del usuario cliente
    @Transactional
    public CustomerProfileResponseDTO createProfileCustomer(CustomerProfileRequestDTO customerProfileRequestDTO) {

        UserResponseDTO userResponseDTO = userService.createUser(customerProfileMapper.createUserRequestDTO(customerProfileRequestDTO));
        
        CustomerProfile customerProfile = customerProfileMapper.convertToEntity(customerProfileRequestDTO);

        customerProfileRepository.save(customerProfile);
        return customerProfileMapper.convertToDTO(customerProfile);
        
    }

    //Mostra perfil de un usuario cliente en especifico por el userId
    @Transactional(readOnly = true)
    public CustomerProfileResponseDTO getCustomerProfileByUserId(Long userId) {
        CustomerProfile customerProfile = customerProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));

        return customerProfileMapper.convertToDTO(customerProfile);
    }

    @Transactional(readOnly = true)
    public List<CustomerProfileResponseDTO> getCustomerProfileByName(String name) {
    List<CustomerProfile> customers = customerProfileRepository.getCustomerProfileByName(name)
        .orElseThrow(() -> new UserNotFound("Nombre no encontrado"));

    return customerProfileMapper.convertToListDTO(customers);
    }

    @Transactional(readOnly = true)
    public List<CustomerProfileResponseDTO> findByDietType(String dietType) {
    List<CustomerProfile> customers = customerProfileRepository.findByDietType(dietType)
        .orElseThrow(() -> new UserNotFound("Dieta no encontrada"));

    return customerProfileMapper.convertToListDTO(customers);
    }

    @Transactional
    public CustomerProfileResponseDTO getCustomerProfileById(Long id) {
        CustomerProfile customerProfile = customerProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));

        return customerProfileMapper.convertToDTO(customerProfile);
    }

    @Transactional
    public List<CustomerProfileResponseDTO> getAllCustomerProfile() {
        List<CustomerProfile> customerProfiles = customerProfileRepository.findAll();
        return customerProfileMapper.convertToListDTO(customerProfiles);
    }

    @Transactional
    public CustomerProfileResponseDTO updateCustomerProfile(Long id, CustomerProfileRequestDTO customerProfileRequestDTO) {
        CustomerProfile customerProfile  = customerProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("Perfil de usuario no encontrado con el numero: "+id));

        customerProfile = customerProfileRepository.save(customerProfile);

        return customerProfileMapper.convertToDTO(customerProfile);
    }

    @Transactional
    public void deleteCustomerProfile(Long id){
        customerProfileRepository.deleteById(id);
    }
}
