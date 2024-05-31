package com.nutrilife.fitnessservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nutrilife.fitnessservice.exception.SpecialistNotFoundException;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;

@ExtendWith(MockitoExtension.class)
public class SpecialistProfileServiceTest {

    @Mock
    private SpecialistProfileRepository specialistProfileRepository;

    @Mock 
    private SpecialistProfileMapper specialistProfileMapper;

    @InjectMocks
    private SpecialistProfileService specialistProfileService;

    //FILTRO POR ID
    @Test
    public void getSpecialistProfileById_ExistingId() {
        // Arrange
        Long id = 1L;
        SpecialistProfile specialistProfile = new SpecialistProfile();
        specialistProfile.setSpecId(id);
        when(specialistProfileRepository.findById(id)).thenReturn(Optional.of(specialistProfile));

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setSpecId(specialistProfile.getSpecId());
        when(specialistProfileMapper.convertToDTO(specialistProfile)).thenReturn(responseDTO);

        // Act
        SpecialistProfileResponseDTO result = specialistProfileService.getSpecialistProfileById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getSpecId());
    }

    // POR ID
    @Test
    public void getSpecialistProfilById_SpecialistNotFound_ThrowsException() {
        // Arrange
        Long Spe_id = 2L;

        when(specialistProfileRepository.findById(Spe_id)).thenReturn(Optional.empty());

        // Act
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
            specialistProfileService.getSpecialistProfileById(Spe_id);
        });

        // Assert
        assertEquals("Especialista no encontrado", exception.getMessage());
    }


    //FILTRO POR NAME
    @Test
    public void getSpecialistProfilesByName_ExistingName() {
        // Arrange
        String name = "Juan Pérez";
        SpecialistProfile specialistProfile1 = new SpecialistProfile();
        specialistProfile1.setSpecId(1L);
        specialistProfile1.setName(name);
    
        SpecialistProfile specialistProfile2 = new SpecialistProfile();
        specialistProfile2.setSpecId(2L);
        specialistProfile2.setName(name);

        List<SpecialistProfile> specialistProfiles = Arrays.asList(specialistProfile1, specialistProfile2);
        when(specialistProfileRepository.findByName(name)).thenReturn(specialistProfiles);

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialistProfile1.getSpecId());
        responseDTO1.setName(specialistProfile1.getName());
    
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialistProfile2.getSpecId());
        responseDTO2.setName(specialistProfile2.getName());

        List<SpecialistProfileResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);
        when(specialistProfileMapper.convertToListDTO(specialistProfiles)).thenReturn(responseDTOs);

        // Act
        List<SpecialistProfileResponseDTO> result = specialistProfileService.getSpecialistProfilesByName(name);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSpecId());
        assertEquals(2L, result.get(1).getSpecId());
        assertEquals(name, result.get(0).getName());
        assertEquals(name, result.get(1).getName());
    }

    // POR NAME
    @Test
    public void getSpecialistProfilesByName_NameNotFound() {
        // Arrange
        String name = "Nonexistent Name";
        List<SpecialistProfile> emptyList = Arrays.asList();
        when(specialistProfileRepository.findByName(name)).thenReturn(emptyList);

        // Act & Assert
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
           specialistProfileService.getSpecialistProfilesByName(name);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("Especialista no encontrado con el nombre: " + name, exception.getMessage());
    }

    //FILTRO POR OCCUPATION
    @Test
    public void getSpecialistProfilesByOccupation_ExistingOccupation() {
        // Arrange
        String occupation = "Nutricionista";
        SpecialistProfile specialistProfile1 = new SpecialistProfile();
        specialistProfile1.setSpecId(1L);
        specialistProfile1.setOcupation(occupation);
    
        SpecialistProfile specialistProfile2 = new SpecialistProfile();
        specialistProfile2.setSpecId(2L);
        specialistProfile2.setOcupation(occupation);

        List<SpecialistProfile> specialistProfiles = Arrays.asList(specialistProfile1, specialistProfile2);
        when(specialistProfileRepository.findByOcuppation(occupation)).thenReturn(specialistProfiles);

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialistProfile1.getSpecId());
        responseDTO1.setOcupation(specialistProfile1.getOcupation());
    
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialistProfile2.getSpecId());
        responseDTO2.setOcupation(specialistProfile2.getOcupation());

        List<SpecialistProfileResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);
        when(specialistProfileMapper.convertToListDTO(specialistProfiles)).thenReturn(responseDTOs);

        // Act
        List<SpecialistProfileResponseDTO> result = specialistProfileService.getSpecialistProfilesByOccupation(occupation);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSpecId());
        assertEquals(2L, result.get(1).getSpecId());
        assertEquals(occupation, result.get(0).getOcupation());
        assertEquals(occupation, result.get(1).getOcupation());
    }

    //POR OCCUPATION
    @Test
    public void getSpecialistProfilesByOccupation_OccupationNotFound() {
        // Arrange
        String occupation = "Ocupacion no existente";
        List<SpecialistProfile> emptyList = Arrays.asList();
        when(specialistProfileRepository.findByOcuppation(occupation)).thenReturn(emptyList);
        // Act & Assert
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
            specialistProfileService.getSpecialistProfilesByOccupation(occupation);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("Especialista no encontrado con la ocupación: " + occupation, exception.getMessage());
    }

    //FILTRO POR AGE
    @Test
    public void getSpecialistProfilesByAge_ExistingAge() {
        // Arrange
        Integer age = 35;
        SpecialistProfile specialistProfile1 = new SpecialistProfile();
        specialistProfile1.setSpecId(1L);
        specialistProfile1.setAge(age);
    
        SpecialistProfile specialistProfile2 = new SpecialistProfile();
        specialistProfile2.setSpecId(2L);
        specialistProfile2.setAge(age);

        List<SpecialistProfile> specialistProfiles = Arrays.asList(specialistProfile1, specialistProfile2);
        when(specialistProfileRepository.findByAge(age)).thenReturn(specialistProfiles);

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialistProfile1.getSpecId());
        responseDTO1.setAge(specialistProfile1.getAge());
    
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialistProfile2.getSpecId());
        responseDTO2.setAge(specialistProfile2.getAge());

        List<SpecialistProfileResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);
        when(specialistProfileMapper.convertToListDTO(specialistProfiles)).thenReturn(responseDTOs);

        // Act
        List<SpecialistProfileResponseDTO> result = specialistProfileService.getSpecialistProfilesByAge(age);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSpecId());
        assertEquals(2L, result.get(1).getSpecId());
        assertEquals(age, result.get(0).getAge());
        assertEquals(age, result.get(1).getAge());
    }

    //POR AGE
    @Test
    public void getSpecialistProfilesByAge_AgeNotFound() {
        // Arrange
        Integer age = 45;
        List<SpecialistProfile> emptyList = Arrays.asList();
        when(specialistProfileRepository.findByAge(age)).thenReturn(emptyList);
        // Act & Assert
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
            specialistProfileService.getSpecialistProfilesByAge(age);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("Especialista no encontrado con la edad: " + age, exception.getMessage());
    }

    @Test
    public void getSpecialistProfilesByAgeRange_FoundSpecialists() {
        // Arrange
        Integer minAge = 30;
        Integer maxAge = 40;
        
        SpecialistProfile specialistProfile1 = new SpecialistProfile();
        specialistProfile1.setSpecId(1L);
        specialistProfile1.setAge(35);
        
        SpecialistProfile specialistProfile2 = new SpecialistProfile();
        specialistProfile2.setSpecId(2L);
        specialistProfile2.setAge(38);

        List<SpecialistProfile> specialistProfiles = Arrays.asList(specialistProfile1, specialistProfile2);
        when(specialistProfileRepository.findByAgeRange(minAge, maxAge)).thenReturn(specialistProfiles);

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialistProfile1.getSpecId());
        responseDTO1.setAge(specialistProfile1.getAge());
        
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialistProfile2.getSpecId());
        responseDTO2.setAge(specialistProfile2.getAge());

        List<SpecialistProfileResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);
        when(specialistProfileMapper.convertToListDTO(specialistProfiles)).thenReturn(responseDTOs);

        // Act
        List<SpecialistProfileResponseDTO> result = specialistProfileService.getSpecialistProfilesByAgeRange(minAge, maxAge);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSpecId());
        assertEquals(2L, result.get(1).getSpecId());
        assertTrue(result.get(0).getAge() >= minAge && result.get(0).getAge() <= maxAge);
        assertTrue(result.get(1).getAge() >= minAge && result.get(1).getAge() <= maxAge);
    }

    @Test
    public void getSpecialistProfilesByAgeRange_NoSpecialistsFound() {
        // Arrange
        int minAge = 40;
        int maxAge = 50;
        List<SpecialistProfile> emptyList = Arrays.asList();
        when(specialistProfileRepository.findByAgeRange(minAge, maxAge)).thenReturn(emptyList);

        // Act & Assert
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
            specialistProfileService.getSpecialistProfilesByAgeRange(minAge, maxAge);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("Especialistas no encontrados en el rango de edad: " + minAge + " to " + maxAge, exception.getMessage());
    }

    //FILTRO POR AGE
    @Test
    public void getSpecialistProfilesByScore_ExistingScore() {
        // Arrange
        Integer score = 4;
        SpecialistProfile specialistProfile1 = new SpecialistProfile();
        specialistProfile1.setSpecId(1L);
        specialistProfile1.setScore(score);
    
        SpecialistProfile specialistProfile2 = new SpecialistProfile();
        specialistProfile2.setSpecId(2L);
        specialistProfile2.setScore(score);

        List<SpecialistProfile> specialistProfiles = Arrays.asList(specialistProfile1, specialistProfile2);
        when(specialistProfileRepository.findByScore(score)).thenReturn(specialistProfiles);

        // Mocking mapper
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialistProfile1.getSpecId());
        responseDTO1.setAge(specialistProfile1.getScore());
    
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialistProfile2.getSpecId());
        responseDTO2.setAge(specialistProfile2.getScore());

        List<SpecialistProfileResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);
        when(specialistProfileMapper.convertToListDTO(specialistProfiles)).thenReturn(responseDTOs);

        // Act
        List<SpecialistProfileResponseDTO> result = specialistProfileService.getSpecialistProfilesByScore(score);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getSpecId());
        assertEquals(2L, result.get(1).getSpecId());
        assertEquals(score, result.get(0).getAge());
        assertEquals(score, result.get(1).getAge());
    }

    //POR SCORE
    @Test
    public void getSpecialistProfilesByScore_ScoreNotFound() {
        // Arrange
        Integer score = 3;
        List<SpecialistProfile> emptyList = Arrays.asList();
        when(specialistProfileRepository.findByScore(score)).thenReturn(emptyList);
        // Act & Assert
        SpecialistNotFoundException exception = assertThrows(SpecialistNotFoundException.class, () -> {
            specialistProfileService.getSpecialistProfilesByScore(score);
        });

        // Assert
        assertNotNull(exception);
        assertEquals("Especialista no encontrado con el puntaje: " + score, exception.getMessage());
    }

}