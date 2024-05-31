package com.nutrilife.fitnessservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import com.nutrilife.fitnessservice.mapper.CustomerProfileMapper;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileRequestDTO;
import com.nutrilife.fitnessservice.model.dto.CustomerProfileResponseDTO;
import com.nutrilife.fitnessservice.model.dto.UserRequestDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.model.entity.User;
import com.nutrilife.fitnessservice.repository.CustomerProfileRepository;
import com.nutrilife.fitnessservice.service.CustomerProfileService;
import com.nutrilife.fitnessservice.service.UserService;

@ExtendWith(MockitoExtension.class)
public class CustomerProfileServiceTest {

    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @Mock
    private CustomerProfileMapper customerProfileMapper1;

    @Mock
    private UserService userService;

    private CustomerProfileMapper customerProfileMapper;

    @BeforeEach
    public void setUp() {
        customerProfileMapper = new CustomerProfileMapper(new ModelMapper());
    }

    @InjectMocks
    private CustomerProfileService customerProfileService;

    @Test
    public void TestCreateProfileCustomer() {

        CustomerProfileRequestDTO requestDTO = new CustomerProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setAlergies("peanut alergie");
        requestDTO.setDietRestriction("peanut");
        requestDTO.setDietType("vegetarian");
        requestDTO.setHeight(1.75F);
        requestDTO.setWeight(65.8F);

        User user = new User();
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setUserId(1L);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail(requestDTO.getEmail());
        userRequestDTO.setPassword(requestDTO.getPassword());

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(1L);
        customer.setAge(requestDTO.getAge());
        customer.setName(requestDTO.getName());
        customer.setPhoneNumber(requestDTO.getPhoneNumber());
        customer.setAlergies(requestDTO.getAlergies());
        customer.setDietRestriction(requestDTO.getDietRestriction());
        customer.setDietType(requestDTO.getDietType());
        customer.setHeight(requestDTO.getHeight());
        customer.setWeight(requestDTO.getWeight());
        customer.setUser(user);

        CustomerProfileResponseDTO responseDTO = new CustomerProfileResponseDTO();
        responseDTO.setAge(customer.getAge());
        responseDTO.setName(customer.getName());
        responseDTO.setCustId(customer.getCustId());
        responseDTO.setEmail(customer.getUser().getEmail());
        responseDTO.setPhoneNumber(customer.getPhoneNumber());
        responseDTO.setAlergies(customer.getAlergies());
        responseDTO.setDietRestriction(customer.getDietRestriction());
        responseDTO.setDietType(customer.getDietType());
        responseDTO.setHeight(customer.getHeight());
        responseDTO.setWeight(customer.getWeight());

        when(customerProfileMapper1.createUserRequestDTO(requestDTO)).thenReturn(userRequestDTO);
        when(userService.createUser(userRequestDTO)).thenReturn(user);
        when(customerProfileMapper1.convertToEntity(requestDTO, user)).thenReturn(customer);
        when(customerProfileRepository.save(customer)).thenReturn(customer);
        when(customerProfileMapper1.convertToDTO(customer)).thenReturn(responseDTO);

        UserRequestDTO mapper1 = customerProfileMapper.createUserRequestDTO(requestDTO);
        assertNotNull(mapper1);
        CustomerProfile mapper2 = customerProfileMapper.convertToEntity(requestDTO, user);
        assertNotNull(mapper2);
        CustomerProfileResponseDTO mapper3 = customerProfileMapper.convertToDTO(customer);
        assertNotNull(mapper3);

        CustomerProfileResponseDTO result = customerProfileService.createProfileCustomer(requestDTO);

        assertNotNull(result);
        assertEquals(customer.getCustId(), result.getCustId());
        assertEquals(customer.getAge(), result.getAge());
        assertEquals(customer.getAlergies(), result.getAlergies());
        assertEquals(customer.getDietRestriction(), result.getDietRestriction());
        assertEquals(customer.getDietType(), result.getDietType());
        assertEquals(customer.getHeight(), result.getHeight());
        assertEquals(customer.getWeight(), result.getWeight());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());

        verify(customerProfileMapper1, times(1)).createUserRequestDTO(requestDTO);
        verify(userService, times(1)).createUser(userRequestDTO);
        verify(customerProfileMapper1, times(1)).convertToEntity(requestDTO, user);
        verify(customerProfileRepository, times(1)).save(customer);
        verify(customerProfileMapper1, times(1)).convertToDTO(customer);
    }

    @Test
    public void TestGetCustomerProfileByUserId_ExistingUserId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(id);

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(id);
        customer.setUser(user);
        when(customerProfileRepository.findByUserId(id)).thenReturn(Optional.of(customer));

        CustomerProfileResponseDTO responseDTO = new CustomerProfileResponseDTO();
        responseDTO.setCustId(id);
        when(customerProfileMapper1.convertToDTO(customer)).thenReturn(responseDTO);

        CustomerProfileResponseDTO mapper1 = customerProfileMapper.convertToDTO(customer);
        assertNotNull(mapper1);

        CustomerProfileResponseDTO result = customerProfileService.getCustomerProfileByUserId(id);

        assertNotNull(result);
        assertEquals(id, result.getCustId());

        verify(customerProfileRepository, times(1)).findByUserId(id);
        verify(customerProfileMapper1, times(1)).convertToDTO(any(CustomerProfile.class));

    }

    @Test
    public void TestGetCustomerProfileByUserId_NonExistingUserId() {
        Long id = 999L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(id);
        customer.setUser(user);
        when(customerProfileRepository.findByUserId(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.getCustomerProfileByUserId(id);
        });

        verify(customerProfileRepository, times(1)).findByUserId(id);
        verify(customerProfileMapper1, times(0)).convertToDTO(any(CustomerProfile.class));

    }

    @Test
    public void TestGetCustomerProfileByName_ExistingName() {
        String name = "Diego";

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setCustId(1L);
        customer1.setUser(user1);
        customer1.setName(name + "1");
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setCustId(2L);
        customer2.setUser(user2);
        customer1.setName(name + "2");
        List<CustomerProfile> customerList = Arrays.asList(customer1, customer2);

        when(customerProfileRepository.getCustomerProfileByName(name)).thenReturn(Optional.of(customerList));

        CustomerProfileResponseDTO responseDTO1 = new CustomerProfileResponseDTO();
        responseDTO1.setName(name + "1");
        CustomerProfileResponseDTO responseDTO2 = new CustomerProfileResponseDTO();
        responseDTO2.setName(name + "2");
        List<CustomerProfileResponseDTO> expectedResponse = Arrays.asList(responseDTO1, responseDTO2);

        when(customerProfileMapper1.convertToListDTO(customerList)).thenReturn(expectedResponse);

        List<CustomerProfileResponseDTO> mapper1 = customerProfileMapper.convertToListDTO(customerList);
        assertNotNull(mapper1);

        List<CustomerProfileResponseDTO> result = customerProfileService.getCustomerProfileByName(name);

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        verify(customerProfileRepository, times(1)).getCustomerProfileByName(name);
        verify(customerProfileMapper1, times(1)).convertToListDTO(customerList);
    }

    @Test
    public void TestGetCustomerProfileByName_NonExistingName() {
        String name = "Carla";

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setCustId(1L);
        customer1.setUser(user1);
        customer1.setName(name + "1");
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setCustId(2L);
        customer2.setUser(user2);
        customer1.setName(name + "2");
        List<CustomerProfile> customerList = Arrays.asList(customer1, customer2);

        when(customerProfileRepository.getCustomerProfileByName(name)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.getCustomerProfileByName(name);
        });

        verify(customerProfileRepository, times(1)).getCustomerProfileByName(name);
        verify(customerProfileMapper1, times(0)).convertToListDTO(customerList);
    }

    @Test
    public void TestFindByDietType_ExistingDietType() {
        String dietType = "vegetarian";

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setCustId(1L);
        customer1.setUser(user1);
        customer1.setDietType(dietType);
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setCustId(2L);
        customer2.setUser(user2);
        customer1.setDietType(dietType);
        List<CustomerProfile> customerList = Arrays.asList(customer1, customer2);

        when(customerProfileRepository.findByDietType(dietType)).thenReturn(Optional.of(customerList));

        CustomerProfileResponseDTO responseDTO1 = new CustomerProfileResponseDTO();
        responseDTO1.setName(dietType);
        CustomerProfileResponseDTO responseDTO2 = new CustomerProfileResponseDTO();
        responseDTO2.setName(dietType);
        List<CustomerProfileResponseDTO> expectedResponse = Arrays.asList(responseDTO1, responseDTO2);

        when(customerProfileMapper1.convertToListDTO(customerList)).thenReturn(expectedResponse);

        List<CustomerProfileResponseDTO> mapper1 = customerProfileMapper.convertToListDTO(customerList);
        assertNotNull(mapper1);

        List<CustomerProfileResponseDTO> result = customerProfileService.findByDietType(dietType);

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        verify(customerProfileRepository, times(1)).findByDietType(dietType);
        verify(customerProfileMapper1, times(1)).convertToListDTO(customerList);
    }

    @Test
    public void TestFindByDietType_NonExistingDietType() {
        String dietType = "vegetarian";

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setCustId(1L);
        customer1.setUser(user1);
        customer1.setDietType(dietType);
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setCustId(2L);
        customer2.setUser(user2);
        customer1.setDietType(dietType);
        List<CustomerProfile> customerList = Arrays.asList(customer1, customer2);

        when(customerProfileRepository.findByDietType(dietType)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.findByDietType(dietType);
        });

        verify(customerProfileRepository, times(1)).findByDietType(dietType);
        verify(customerProfileMapper1, times(0)).convertToListDTO(customerList);
    }

    @Test
    public void TestGetCustomerProfileById_ExistingId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(id);
        customer.setUser(user);
        when(customerProfileRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerProfileResponseDTO responseDTO = new CustomerProfileResponseDTO();
        responseDTO.setCustId(id);
        when(customerProfileMapper1.convertToDTO(customer)).thenReturn(responseDTO);

        CustomerProfileResponseDTO mapper1 = customerProfileMapper.convertToDTO(customer);
        assertNotNull(mapper1);

        CustomerProfileResponseDTO result = customerProfileService.getCustomerProfileById(id);
        assertNotNull(result);
        assertEquals(id, result.getCustId());

        verify(customerProfileRepository, times(1)).findById(id);
        verify(customerProfileMapper1, times(1)).convertToDTO(any(CustomerProfile.class));

    }

    @Test
    public void TestGetCustomerProfileById_NonExistingId() {
        Long id = 999L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(id);
        customer.setUser(user);
        when(customerProfileRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.getCustomerProfileById(id);
        });

        verify(customerProfileRepository, times(1)).findById(id);
        verify(customerProfileMapper1, times(0)).convertToDTO(any(CustomerProfile.class));

    }

    @Test
    public void TestGetAllCustomerProfile() {

        User user1 = new User();
        user1.setEmail("test1@mail.com");
        User user2 = new User();
        user2.setEmail("test2@mail.com");
        CustomerProfile customer1 = new CustomerProfile();
        customer1.setCustId(1L);
        customer1.setUser(user1);
        CustomerProfile customer2 = new CustomerProfile();
        customer2.setCustId(2L);
        customer2.setUser(user2);
        List<CustomerProfile> customerList = Arrays.asList(customer1, customer2);

        when(customerProfileRepository.findAll()).thenReturn(customerList);

        CustomerProfileResponseDTO responseDTO1 = new CustomerProfileResponseDTO();
        responseDTO1.setCustId(1L);
        CustomerProfileResponseDTO responseDTO2 = new CustomerProfileResponseDTO();
        responseDTO2.setCustId(2L);
        List<CustomerProfileResponseDTO> expectedResponse = Arrays.asList(responseDTO1, responseDTO2);

        when(customerProfileMapper1.convertToListDTO(customerList)).thenReturn(expectedResponse);

        List<CustomerProfileResponseDTO> mapper1 = customerProfileMapper.convertToListDTO(customerList);
        assertNotNull(mapper1);

        List<CustomerProfileResponseDTO> result = customerProfileService.getAllCustomerProfile();

        assertNotNull(result);
        assertEquals(expectedResponse.size(), result.size());

        verify(customerProfileRepository, times(1)).findAll();
        verify(customerProfileMapper1, times(1)).convertToListDTO(customerList);
    }

    @Test
    public void TestUpdateCustomerProfile_ExistingUserId() {
        Long id = 1L;

        User user = new User();
        user.setEmail("test1@mail.com");
        user.setPassword("password%test1");
        user.setUserId(1L);

        CustomerProfileRequestDTO requestDTO = new CustomerProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setAlergies("peanut alergie");
        requestDTO.setDietRestriction("peanut");
        requestDTO.setDietType("vegetarian");
        requestDTO.setHeight(1.75F);
        requestDTO.setWeight(65.8F);

        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(id);
        customer.setAge(requestDTO.getAge());
        customer.setName(requestDTO.getName());
        customer.setPhoneNumber(requestDTO.getPhoneNumber());
        customer.setAlergies(requestDTO.getAlergies());
        customer.setDietRestriction(requestDTO.getDietRestriction());
        customer.setDietType(requestDTO.getDietType());
        customer.setHeight(requestDTO.getHeight());
        customer.setWeight(requestDTO.getWeight());
        customer.setUser(user);

        CustomerProfileResponseDTO responseDTO = new CustomerProfileResponseDTO();
        responseDTO.setAge(customer.getAge());
        responseDTO.setName(customer.getName());
        responseDTO.setCustId(customer.getCustId());
        responseDTO.setEmail(customer.getUser().getEmail());
        responseDTO.setPhoneNumber(customer.getPhoneNumber());
        responseDTO.setAlergies(customer.getAlergies());
        responseDTO.setDietRestriction(customer.getDietRestriction());
        responseDTO.setDietType(customer.getDietType());
        responseDTO.setHeight(customer.getHeight());
        responseDTO.setWeight(customer.getWeight());

        when(customerProfileRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerProfileRepository.save(customer)).thenReturn(customer);
        when(customerProfileMapper1.convertToDTO(customer)).thenReturn(responseDTO);

        CustomerProfileResponseDTO mapper1 = customerProfileMapper.convertToDTO(customer);
        assertNotNull(mapper1);

        CustomerProfileResponseDTO result = customerProfileService.updateCustomerProfile(id, requestDTO);

        assertNotNull(result);
        assertEquals(id, result.getCustId());
        assertEquals(customer.getUser().getEmail(), result.getEmail());
        assertEquals(customer.getCustId(), result.getCustId());
        assertEquals(customer.getAge(), result.getAge());
        assertEquals(customer.getAlergies(), result.getAlergies());
        assertEquals(customer.getDietRestriction(), result.getDietRestriction());
        assertEquals(customer.getDietType(), result.getDietType());
        assertEquals(customer.getHeight(), result.getHeight());
        assertEquals(customer.getWeight(), result.getWeight());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getPhoneNumber(), result.getPhoneNumber());

        verify(customerProfileRepository, times(1)).findById(id);
        verify(customerProfileRepository, times(1)).save(customer);
        verify(customerProfileMapper1, times(1)).convertToDTO(customer);
    }

    @Test
    public void TestUpdateCustomerProfile_NonExistingUserId() {
        Long id = 999L;

        CustomerProfileRequestDTO requestDTO = new CustomerProfileRequestDTO();
        requestDTO.setAge(25);
        requestDTO.setEmail("test1@mail.com");
        requestDTO.setName("User Test");
        requestDTO.setPassword("password%test1");
        requestDTO.setPhoneNumber("1234567890");
        requestDTO.setAlergies("peanut alergie");
        requestDTO.setDietRestriction("peanut");
        requestDTO.setDietType("vegetarian");
        requestDTO.setHeight(1.75F);
        requestDTO.setWeight(65.8F);

        when(customerProfileRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.updateCustomerProfile(id, requestDTO);
        });

        verify(customerProfileRepository, times(1)).findById(id);
        verify(customerProfileRepository, times(0)).save(any(CustomerProfile.class));
        verify(customerProfileMapper1, times(0)).convertToDTO(any(CustomerProfile.class));
    }

    @Test
    public void testDeleteCustomerProfile_ExistingUserId() {
        CustomerProfile customer = new CustomerProfile();
        customer.setCustId(1L);
        customer.setAge(25);
        customer.setName("Diego");
        customer.setPhoneNumber("1234567890");
        customer.setAlergies("peanut alergie");
        customer.setDietRestriction("peanut");
        customer.setDietType("vegetarian");
        customer.setHeight(1.68F);
        customer.setWeight(68.5F);

        doThrow(new UserNotFound()).when(customerProfileRepository).deleteById(customer.getCustId());

        assertThrows(UserNotFound.class, () -> {
            customerProfileService.deleteCustomerProfile(customer.getCustId());
        });

        verify(customerProfileRepository, times(1)).deleteById(customer.getCustId());
    }
}