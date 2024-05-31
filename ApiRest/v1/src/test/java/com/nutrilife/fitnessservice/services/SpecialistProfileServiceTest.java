package com.nutrilife.fitnessservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.SpecialistProfileMapper;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.SpecialistProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;
import com.nutrilife.fitnessservice.service.SpecialistProfileService;
import com.nutrilife.fitnessservice.service.UserService;

@ExtendWith(MockitoExtension.class)
public class SpecialistProfileServiceTest {
    
    @Mock
    private UserService userService;

    @Mock
    private SpecialistProfileRepository specialistProfileRepository;


    @Mock
    private SpecialistProfileMapper specialistProfileMapper1;


    private SpecialistProfileMapper specialistProfileMapper;

    @BeforeEach
    public void setUp() {
        specialistProfileMapper = new SpecialistProfileMapper(new ModelMapper());
    }

    @InjectMocks
    private SpecialistProfileService specialistProfileService;

    @Test
    public void testCreateSpecialistProfile() {

        SpecialistProfileRequestDTO requestDTO = new SpecialistProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setOcupation("Specialist");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setScore(4);
    
        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setUserId(1L);
    
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(requestDTO.getEmail());
        userRequestDTO.setPassword(requestDTO.getPassword());
    
        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setSpecId(1L);
        specialist.setAge(requestDTO.getAge());
        specialist.setName(requestDTO.getName());
        specialist.setOcupation(requestDTO.getOcupation());
        specialist.setPhoneNumber(requestDTO.getPhoneNumber());
        specialist.setScore(requestDTO.getScore());
        specialist.setUser(user);
        
        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setAge(specialist.getAge());
        responseDTO.setEmail(specialist.getUser().getEmail());
        responseDTO.setName(specialist.getName());
        responseDTO.setOcupation(specialist.getOcupation());
        responseDTO.setPhoneNumber(specialist.getPhoneNumber());
        responseDTO.setScore(specialist.getScore());
        responseDTO.setSpecId(specialist.getSpecId());
        responseDTO.setStudCertificate(specialist.getStudCertificate());
    
        when(specialistProfileMapper1.createUserRequestDTO(requestDTO)).thenReturn(userRequestDTO);
        when(userService.createUser(userRequestDTO)).thenReturn(user);
        when(specialistProfileMapper1.convertToEntity(requestDTO, user)).thenReturn(specialist);
        when(specialistProfileRepository.save(specialist)).thenReturn(specialist);
        when(specialistProfileMapper1.convertToDTO(specialist)).thenReturn(responseDTO);
        
        UserRequestDTO mapper1 = specialistProfileMapper.createUserRequestDTO(requestDTO);
        assertNotNull(mapper1);
        SpecialistProfile mapper2 = specialistProfileMapper.convertToEntity(requestDTO, user);
        assertNotNull(mapper2);
        SpecialistProfileResponseDTO mapper3 = specialistProfileMapper.convertToDTO(specialist);
        assertNotNull(mapper3);

        SpecialistProfileResponseDTO result = specialistProfileService.createProfileSpecialist(requestDTO);
    
        assertNotNull(result);
        assertEquals(specialist.getSpecId(), result.getSpecId());
        assertEquals(specialist.getAge(), result.getAge());
        assertEquals(specialist.getName(), result.getName());
        assertEquals(specialist.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(specialist.getOcupation(), result.getOcupation());
        assertEquals(specialist.getScore(), result.getScore());
        assertEquals(specialist.getStudCertificate(), result.getStudCertificate());
    
        verify(specialistProfileMapper1, times(1)).createUserRequestDTO(requestDTO);
        verify(userService, times(1)).createUser(userRequestDTO);
        verify(specialistProfileMapper1, times(1)).convertToEntity(requestDTO, user);
        verify(specialistProfileRepository, times(1)).save(specialist);
        verify(specialistProfileMapper1, times(1)).convertToDTO(specialist);
    }

    @Test
    public void testGetAllAccounts() {

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        SpecialistProfile specialist1 = new SpecialistProfile();
        specialist1.setSpecId(1L);
        specialist1.setUser(user1);
        SpecialistProfile specialist2 = new SpecialistProfile();
        specialist2.setSpecId(2L);
        specialist2.setUser(user2);
        List<SpecialistProfile> specialistList = Arrays.asList(specialist1,specialist2);

        when(specialistProfileRepository.findAll()).thenReturn(specialistList);
    
        SpecialistProfileResponseDTO responseDTO1 = new SpecialistProfileResponseDTO();
        responseDTO1.setSpecId(specialist1.getSpecId());
        SpecialistProfileResponseDTO responseDTO2 = new SpecialistProfileResponseDTO();
        responseDTO2.setSpecId(specialist2.getSpecId());

        List<SpecialistProfileResponseDTO> expectedResponse = Arrays.asList(responseDTO1,responseDTO2);
        when(specialistProfileMapper1.convertToListDTO(specialistList)).thenReturn(expectedResponse);

        List<SpecialistProfileResponseDTO> mapper1 = specialistProfileMapper.convertToListDTO(specialistList);
        assertNotNull(mapper1);

        List<SpecialistProfileResponseDTO> result = specialistProfileService.getAllSpecialistProfile();

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        verify(specialistProfileRepository, times(1)).findAll();
        verify(specialistProfileMapper1, times(1)).convertToListDTO(specialistList);
    }

    @Test
    public void testGetSpecialistProfileByUserId_ExistingUserId() {

        Long id = 1L;
        User user = new User();
        user.setEmail("test1@mail.com");
        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setSpecId(id);
        specialist.setUser(user);
        when(specialistProfileRepository.findByUserId(id)).thenReturn(Optional.of(specialist));

        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setSpecId(specialist.getSpecId());
        when(specialistProfileMapper1.convertToDTO(specialist)).thenReturn(responseDTO);

        SpecialistProfileResponseDTO mapper1 = specialistProfileMapper.convertToDTO(specialist);
        assertNotNull(mapper1);

        SpecialistProfileResponseDTO result = specialistProfileService.getSpecialistProfileByUserId(id);

        assertNotNull(result);
        assertEquals(id, result.getSpecId());
    }

    @Test
    public void testGetSpecialistProfileByUserId_NonExistingUserId() {

        Long id = 999L;
        SpecialistProfileRequestDTO requestDTO = new SpecialistProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setOcupation("Specialist");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setScore(4);

        when(specialistProfileRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            specialistProfileService.updateSpecialistProfile(id, requestDTO);
        });

        verify(specialistProfileRepository, times(1)).findById(id);
        verify(specialistProfileRepository, times(0)).save(any(SpecialistProfile.class));
        verify(specialistProfileMapper1, times(0)).convertToDTO(any(SpecialistProfile.class));
    }

    @Test 
    public void testGetSpecialistProfileById_ExistingId() {

        Long id = 1L;
        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setSpecId(id);
        specialist.setUser(user);
        when(specialistProfileRepository.findById(id)).thenReturn(Optional.of(specialist));

        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setSpecId(specialist.getSpecId());
        when(specialistProfileMapper1.convertToDTO(specialist)).thenReturn(responseDTO);

        SpecialistProfileResponseDTO mapper1 = specialistProfileMapper.convertToDTO(specialist);
        assertNotNull(mapper1);

        SpecialistProfileResponseDTO result = specialistProfileService.getSpecialistProfileById(id);

        assertNotNull(result);
        assertEquals(id, result.getSpecId());
    }

    @Test
    public void testGetSpecialistProfileById_NonExistingId() {
        
        Long id = 999L;
        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setSpecId(id);
        //when(specialistProfileRepository.findById(id)).thenReturn(Optional.empty());
        doThrow(new UserNotFound("Perfil de usuario no encontrado con el numero: "+id)).when(specialistProfileRepository).findById(id);

        assertThrows(UserNotFound.class, () -> specialistProfileService.getSpecialistProfileById(id));

    }

    @Test
    public void testUpdateSpecialistProfile_ExistingUserId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        SpecialistProfileRequestDTO requestDTO = new SpecialistProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setOcupation("Specialist");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setScore(4);

        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setSpecId(id);
        specialist.setAge(requestDTO.getAge());
        specialist.setName(requestDTO.getName());
        specialist.setOcupation(requestDTO.getOcupation());
        specialist.setPhoneNumber(requestDTO.getPhoneNumber());
        specialist.setScore(requestDTO.getScore());
        specialist.setUser(user);

        SpecialistProfileResponseDTO responseDTO = new SpecialistProfileResponseDTO();
        responseDTO.setAge(specialist.getAge());
        responseDTO.setEmail(requestDTO.getEmail());
        responseDTO.setName(specialist.getName());
        responseDTO.setOcupation(specialist.getOcupation());
        responseDTO.setPhoneNumber(specialist.getPhoneNumber());
        responseDTO.setScore(specialist.getScore());
        responseDTO.setSpecId(specialist.getSpecId());
        responseDTO.setStudCertificate(specialist.getStudCertificate());

        when(specialistProfileRepository.findById(id)).thenReturn(Optional.of(specialist));
        when(specialistProfileRepository.save(specialist)).thenReturn(specialist);
        when(specialistProfileMapper1.convertToDTO(specialist)).thenReturn(responseDTO);

        SpecialistProfileResponseDTO mapper1 = specialistProfileMapper.convertToDTO(specialist);
        assertNotNull(mapper1);

        SpecialistProfileResponseDTO result = specialistProfileService.updateSpecialistProfile(id, requestDTO);

        assertNotNull(result);
        assertEquals(id, result.getSpecId());
        assertEquals(specialist.getSpecId(), result.getSpecId());
        assertEquals(specialist.getAge(), result.getAge());
        assertEquals(specialist.getName(), result.getName());
        assertEquals(specialist.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(specialist.getOcupation(), result.getOcupation());
        assertEquals(specialist.getScore(), result.getScore());
        assertEquals(specialist.getStudCertificate(), result.getStudCertificate());

        verify(specialistProfileRepository, times(1)).findById(id);
        verify(specialistProfileRepository, times(1)).save(specialist);
        verify(specialistProfileMapper1, times(1)).convertToDTO(specialist);
    }

    @Test
    public void testDeleteSpecialistProfile_ExistingUserId() {
        SpecialistProfile specialist = new SpecialistProfile();
        specialist.setAge(25);
        specialist.setName("User Test");
        specialist.setOcupation("Specialist");
        specialist.setPhoneNumber("1234567890");
        specialist.setScore(4);
        specialist.setSpecId(1L);
        
        doThrow(new UserNotFound()).when(specialistProfileRepository).deleteById(specialist.getSpecId());

        // Act & Assert
        assertThrows(UserNotFound.class, () -> {
            specialistProfileService.deleteSpecialistProfile(specialist.getSpecId());
        });

        verify(specialistProfileRepository, times(1)).deleteById(specialist.getSpecId());
    }

}
