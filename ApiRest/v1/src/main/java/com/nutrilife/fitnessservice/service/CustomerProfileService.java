package com.nutrilife.fitnessservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.CustomerProfileMapper;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.repository.CustomerProfileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerProfileService {
    
    private final CustomerProfileRepository customerProfileRepository;
    private final CustomerProfileMapper customerProfileMapper;

    //Crear perfil del usuario cliente
    @Transactional
    public CustomerProfileResponseDTO createProfileCustomer(CustomerProfileRequestDTO customerProfileRequestDTO) {
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

        if (customerProfileRequestDTO.getName() != null) {
            customerProfile.setName(customerProfileRequestDTO.getName());
        }
        if (customerProfileRequestDTO.getPhoneNumber() != null) {
            customerProfile.setPhoneNumber(customerProfileRequestDTO.getPhoneNumber());
        }
        if (customerProfileRequestDTO.getWeight() != null) {
            customerProfile.setWeight(customerProfileRequestDTO.getWeight());
        }
        if (customerProfileRequestDTO.getHeight() != null) {
            customerProfile.setHeight(customerProfile.getHeight());
        }
        if (customerProfileRequestDTO.getDietType() != null) {
            customerProfile.setDietType(customerProfileRequestDTO.getDietType());
        }

        customerProfile = customerProfileRepository.save(customerProfile);

        return customerProfileMapper.convertToDTO(customerProfile);
    }

    @Transactional
    public void deleteCustomerProfile(Long id){
        customerProfileRepository.deleteById(id);
    }
}
