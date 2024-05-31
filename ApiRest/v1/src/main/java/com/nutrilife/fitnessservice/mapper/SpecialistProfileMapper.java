package com.nutrilife.fitnessservice.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.User;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SpecialistProfileMapper {

    private final ModelMapper modelMapper;

    public SpecialistProfile convertToEntity(SpecialistProfileRequestDTO specialistProfileRequestDTO, User user) {

        SpecialistProfile specialistProfile = modelMapper.map(specialistProfileRequestDTO, SpecialistProfile.class);
        specialistProfile.setUser(user);
        return specialistProfile;
    }

    public UserRequestDTO createUserRequestDTO(SpecialistProfileRequestDTO specialistProfileRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(specialistProfileRequestDTO.getEmail());
        userRequestDTO.setPassword(specialistProfileRequestDTO.getPassword());
        return userRequestDTO;
    }

    public SpecialistProfileResponseDTO convertToDTO(SpecialistProfile specialistProfile) {
        SpecialistProfileResponseDTO specDTO = modelMapper.map(specialistProfile, SpecialistProfileResponseDTO.class);
        specDTO.setEmail(specialistProfile.getUser().getEmail());
        return specDTO;
    }

    public List<SpecialistProfileResponseDTO> convertToListDTO(List<SpecialistProfile> specialistProfiles) {
        List<SpecialistProfileResponseDTO> specDTOs = specialistProfiles.stream()
                .map(this::convertToDTO)
                .toList();

        List<String> emails = new ArrayList<>();
        specialistProfiles.forEach(profile -> emails.add(profile.getUser().getEmail()));

        IntStream.range(0, specDTOs.size())
                .forEach(i -> specDTOs.get(i).setEmail(emails.get(i % emails.size())));

        return specDTOs;
    }
}
