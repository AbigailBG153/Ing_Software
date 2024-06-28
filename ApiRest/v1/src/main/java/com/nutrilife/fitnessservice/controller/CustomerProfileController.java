package com.nutrilife.fitnessservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.service.CustomerProfileService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerProfileController {
    
    private final CustomerProfileService customerProfileService;
    
    @GetMapping
    public ResponseEntity<List<CustomerProfileResponseDTO>> getAllProfiles(){
        List<CustomerProfileResponseDTO> customers = customerProfileService.getAllCustomerProfile();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfileResponseDTO> getCustomerProfileById(@PathVariable Long id) {
        CustomerProfileResponseDTO customer = customerProfileService.getCustomerProfileById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CustomerProfileResponseDTO>> getCustomerProfileByName(@PathVariable String name) {
        List<CustomerProfileResponseDTO> customers = customerProfileService.getCustomerProfileByName(name);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/dietType/{dietType}")
    public ResponseEntity<List<CustomerProfileResponseDTO>> findByDietType(@PathVariable String dietType) {
        List<CustomerProfileResponseDTO> customers = customerProfileService.findByDietType(dietType);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerProfileResponseDTO> createCutomerProfile(@Validated @RequestBody CustomerProfileRequestDTO customerDTO) {
        CustomerProfileResponseDTO customer = customerProfileService.createProfileCustomer(customerDTO);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerProfileResponseDTO> deleteCustomerProfile(@PathVariable Long id) {
        customerProfileService.deleteCustomerProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}
