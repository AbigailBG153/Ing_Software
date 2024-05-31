package com.nutrilife.fitnessservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.nutrilife.fitnessservice.exception.ValidationUserRegisterException;
import com.nutrilife.fitnessservice.mapper.UserMapper;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.dto.UserResponseDTO;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.UserRespository;
import com.nutrilife.fitnessservice.service.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRespository userRespository;

    @Mock
    private UserMapper userMapper1;

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper(new ModelMapper());
    }

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser_NonExistsEmail() {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("diego1@mail.com");
        requestDTO.setPassword("abcdef%12345");
        
        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setUserId(1L);

        when(userRespository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(userMapper1.convertToEntity(requestDTO)).thenReturn(user);
        when(userRespository.save(user)).thenReturn(user);

        User mapper1 = userMapper.convertToEntity(requestDTO);
        assertNotNull(mapper1);

        User result = userService.createUser(requestDTO);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserId(), result.getUserId());

        verify(userRespository, times(1)).existsByEmail(requestDTO.getEmail());
        verify(userMapper1, times(1)).convertToEntity(requestDTO);
        verify(userRespository, times(1)).save(user);
    }

    @Test
    public void testCreateUser_ExistsEmail() {
        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("diego1@mail.com");
        requestDTO.setPassword("abcdef%12345");
        
        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setUserId(1L);

        when(userRespository.existsByEmail(requestDTO.getEmail())).thenReturn(true);
        
        assertThrows(ValidationUserRegisterException.class, () -> {
            userService.createUser(requestDTO);
        });

        verify(userRespository, times(1)).existsByEmail(requestDTO.getEmail());
        verify(userMapper1, times(0)).convertToEntity(requestDTO);
        verify(userRespository, times(0)).save(user);
    }

    @Test
    public void testGetUserById_ExistsId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("diego1@mail.com");
        user.setPassword("abcdef%12345");
        user.setUserId(id);
        
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPassword(user.getPassword());
        responseDTO.setUserId(user.getUserId());

        when(userRespository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper1.convertToDTO(user)).thenReturn(responseDTO);

        UserResponseDTO mapper1 = userMapper.convertToDTO(user);
        assertNotNull(mapper1);

        UserResponseDTO result = userService.getUserById(id);
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserId(), result.getUserId());

        verify(userRespository, times(1)).findById(id);
        verify(userMapper1, times(1)).convertToDTO(user);
    }

    @Test
    public void testGetUserById_NonExistsId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("diego1@mail.com");
        user.setPassword("abcdef%12345");
        user.setUserId(id);
        
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPassword(user.getPassword());
        responseDTO.setUserId(user.getUserId());

        when(userRespository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(UserNotFound.class, () -> {
            userService.getUserById(id);
        });

        verify(userRespository, times(1)).findById(id);
        verify(userMapper1, times(0)).convertToDTO(user);
    }

    @Test
    public void TestUpdateUser() {
        Long id = 1L;

        UserRequestDTO requestDTO = new UserRequestDTO();
        requestDTO.setEmail("diego1@mail.com");
        requestDTO.setPassword("abcdef%12345");
        
        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setUserId(id);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPassword(user.getPassword());
        responseDTO.setUserId(user.getUserId());

        when(userRespository.findById(id)).thenReturn(Optional.of(user));
        //when(userRespository.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(userRespository.save(user)).thenReturn(user);
        when(userMapper1.convertToDTO(user)).thenReturn(responseDTO);

        UserResponseDTO mapper1 = userMapper.convertToDTO(user);
        assertNotNull(mapper1);

        UserResponseDTO result = userService.updateUser(id, requestDTO);
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getUserId(), result.getUserId());

        verify(userRespository, times(1)).findById(id);
        //verify(userRespository, times(1)).existsByEmail(requestDTO.getEmail());
        verify(userMapper1, times(1)).convertToDTO(user);
        verify(userRespository, times(1)).save(user);
    }

    @Test
    public void TestGetAllUsers() {
        User user1 = new User();
        user1.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        List<User> userList = Arrays.asList(user1, user2);

        when(userRespository.findAll()).thenReturn(userList);

        UserResponseDTO responseDTO1 = new UserResponseDTO();
        responseDTO1.setUserId(user1.getUserId());
        UserResponseDTO responseDTO2 = new UserResponseDTO();
        responseDTO2.setUserId(user2.getUserId());

        List<UserResponseDTO> expectedResponse = Arrays.asList(responseDTO1, responseDTO2);
        when(userMapper1.convertToListDTO(userList)).thenReturn(expectedResponse);

        List<UserResponseDTO> mapper1 = userMapper.convertToListDTO(userList);
        assertNotNull(mapper1);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        verify(userRespository, times(1)).findAll();
        verify(userMapper1, times(1)).convertToListDTO(userList);
    }

    @Test
    public void TestDeleteUser() {
        User user1 = new User();
        user1.setEmail("diego1@mail.com");
        user1.setPassword("abcdef%12345");
        user1.setUserId(1L);

        doThrow(new UserNotFound()).when(userRespository).deleteById(user1.getUserId());

        assertThrows(UserNotFound.class, () -> {
            userService.deleteUser(user1.getUserId());
        });

        verify(userRespository, times(1)).deleteById(user1.getUserId());
    }
}
