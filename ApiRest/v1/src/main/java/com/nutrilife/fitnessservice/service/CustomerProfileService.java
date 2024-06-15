package com.nutrilife.fitnessservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.CustomerProfileMapper;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
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

    //Crear perfil del usuario cliente - Registrarse
    @Transactional
    public CustomerProfileResponseDTO createProfileCustomer(CustomerProfileRequestDTO customerProfileRequestDTO) {

        User user = userService.createUser(customerProfileMapper.createUserRequestDTO(customerProfileRequestDTO));
        
        CustomerProfile customerProfile = customerProfileMapper.convertToEntity(customerProfileRequestDTO);
        customerProfile.setUser(user);

        customerProfileRepository.save(customerProfile);
        CustomerProfileResponseDTO custDTO = customerProfileMapper.convertToDTO(customerProfile);
        custDTO.setEmail(customerProfile.getUser().getEmail());
        return custDTO;
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

    @Transactional(readOnly = true)
    public CustomerProfileResponseDTO getCustomerProfileById(Long id) {
        CustomerProfile customerProfile = customerProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("El usuario no existe"));

        CustomerProfileResponseDTO custDTO = customerProfileMapper.convertToDTO(customerProfile);
        custDTO.setEmail(customerProfile.getUser().getEmail());
        return custDTO;
    }

    @Transactional(readOnly = true)
    public List<CustomerProfileResponseDTO> getAllCustomerProfile() {
        List<CustomerProfile> customerProfiles = customerProfileRepository.findAll();

        List<CustomerProfileResponseDTO> custDTOs = customerProfileMapper.convertToListDTO(customerProfiles);
        List<String> emails = new ArrayList<>();
        customerProfiles.forEach(profile -> emails.add(profile.getUser().getEmail()));

        IntStream.range(0, custDTOs.size())
            .forEach(i -> custDTOs.get(i).setEmail(emails.get(i % emails.size())));
        return custDTOs;
    }

    @Transactional
    public CustomerProfileResponseDTO updateCustomerProfile(Long id, CustomerProfileRequestDTO customerProfileRequestDTO) {
        CustomerProfile customerProfile  = customerProfileRepository.findById(id)
            .orElseThrow(() -> new UserNotFound("Perfil de usuario no encontrado con el numero: "+id));

        customerProfile.setAge(customerProfileRequestDTO.getAge()); 
        customerProfile.setAlergies(customerProfileRequestDTO.getAlergies());
        customerProfile.setDietRestriction(customerProfileRequestDTO.getDietRestriction());
        customerProfile.setDietType(customerProfileRequestDTO.getDietType());
        customerProfile.setHeight(customerProfileRequestDTO.getHeight());
        customerProfile.setName(customerProfileRequestDTO.getName());
        customerProfile.setPhoneNumber(customerProfileRequestDTO.getPhoneNumber());
        customerProfile.setWeight(customerProfileRequestDTO.getWeight());

        customerProfile = customerProfileRepository.save(customerProfile);

        return customerProfileMapper.convertToDTO(customerProfile);
    }

    @Transactional
    public void deleteCustomerProfile(Long id){
        customerProfileRepository.deleteById(id);
    }
}
