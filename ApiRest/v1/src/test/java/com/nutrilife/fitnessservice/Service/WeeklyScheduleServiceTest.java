package com.nutrilife.fitnessservice.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.nutrilife.fitnessservice.exception.ResourceNotFoundException;
import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.mapper.WeeklyScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.model.enums.WeeklyScheduleStatus;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;
import com.nutrilife.fitnessservice.repository.WeeklyScheduleRepository;
import com.nutrilife.fitnessservice.service.WeeklyScheduleService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WeeklyScheduleServiceTest {

    @Mock
    private SpecialistProfileRepository specialistProfileRepository;

    @Mock
    private WeeklyScheduleRepository weeklyScheduleRepository;

    @Mock
    private WeeklyScheduleMapper weeklyScheduleMapper;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private WeeklyScheduleService weeklyScheduleService;
    
    private LocalDate date;
    private DayOfWeek dayOfWeek;

    private WeeklySchedule weeklySchedule;
    private Schedule schedule;
    private ScheduleRequestDTO scheduleRequestDTO;
    private ScheduleResponseDTO scheduleResponseDTO;
    private ScheduleMapper scheduleMapperTest;
    private SpecialistProfile specialistProfile;
    private Long specialistProfileId;
    private WeeklyScheduleResponseDTO weeklyScheduleResponseDTO;
    private WeeklyScheduleRequestDTO weeklyScheduleRequestDTO;
    private WeeklyScheduleMapper weeklyScheduleMapperTest;
    @BeforeEach
    void setUp() {

        scheduleMapperTest = new ScheduleMapper(new ModelMapper());

        weeklyScheduleMapperTest = new WeeklyScheduleMapper(new ModelMapper(), scheduleMapperTest);
        
        date = LocalDate.of(2024, 5, 27);
        dayOfWeek = LocalDate.of(2024, 5, 27).getDayOfWeek();
        specialistProfileId = 1L;
        specialistProfile = new SpecialistProfile();
        specialistProfile.setSpecId(1L);
        
        
        weeklyScheduleRequestDTO = new WeeklyScheduleRequestDTO();
        weeklyScheduleRequestDTO.setStartDate(LocalDate.of(2024, 5, 27));
        weeklyScheduleRequestDTO.setEndDate(LocalDate.of(2024, 6, 2));
        weeklyScheduleRequestDTO.setStatus(WeeklyScheduleStatus.DISABLED.toString());

        weeklySchedule = new WeeklySchedule();
        weeklySchedule.setWeeklyScheduleId(1L);
        weeklySchedule.setStartDate(weeklyScheduleRequestDTO .getStartDate());
        weeklySchedule.setEndDate(weeklyScheduleRequestDTO .getEndDate());
        weeklySchedule.setStatus(WeeklyScheduleStatus.DISABLED); // Establecer el estado predeterminado
        weeklySchedule.setSpecialistProfile(specialistProfile);
        
        scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setDayOfWeek(LocalDate.of(2024, 5, 27).getDayOfWeek().toString());
        scheduleRequestDTO.setDate(LocalDate.of(2024, 5, 27));
        scheduleRequestDTO.setStartTime(LocalTime.of(10, 0));
        scheduleRequestDTO.setEndTime(LocalTime.of(11, 0));
        scheduleRequestDTO.setStatus(ScheduleStatus.DISABLED.toString());

        schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setStatus(ScheduleStatus.valueOf(scheduleRequestDTO.getStatus()));
        schedule.setDate(scheduleRequestDTO.getDate());
        schedule.setDayOfWeek(scheduleRequestDTO.getDayOfWeek());
        schedule.setStartTime(scheduleRequestDTO.getStartTime());
        schedule.setEndTime(scheduleRequestDTO.getEndTime());
        schedule.setWeeklySchedule(weeklySchedule);
        

        weeklySchedule.setScheduleList(Arrays.asList(schedule));
        specialistProfile.setWeeklySchedules(Arrays.asList(weeklySchedule));

        scheduleResponseDTO = new ScheduleResponseDTO();
        scheduleResponseDTO.setScheduleId(1L);
        scheduleResponseDTO.setStatus(schedule.getStatus());
        scheduleResponseDTO.setDate(schedule.getDate());
        scheduleResponseDTO.setStartTime(schedule.getStartTime());
        scheduleResponseDTO.setEndTime(schedule.getEndTime());
        scheduleResponseDTO.setMeeting(null);


        weeklyScheduleResponseDTO = new WeeklyScheduleResponseDTO();
        weeklyScheduleResponseDTO.setWeeklyScheduleId(1L);
        weeklyScheduleResponseDTO.setSpecialistId(1L);
        weeklyScheduleResponseDTO.setStartDate(LocalDate.of(2024, 5, 27));
        weeklyScheduleResponseDTO.setEndDate(LocalDate.of(2024, 6, 2));
        weeklyScheduleResponseDTO.setStatus(weeklyScheduleRequestDTO.getStatus());
        weeklyScheduleResponseDTO.setSchedulesList(Arrays.asList(scheduleResponseDTO));
    }

    @Test
    void testGetAllWeeklySchedules() {
        
        when(weeklyScheduleRepository.findAll()).thenReturn(Arrays.asList(weeklySchedule));
        when(weeklyScheduleMapper.convertToListDTO(Arrays.asList(weeklySchedule))).thenReturn(Arrays.asList(weeklyScheduleResponseDTO));
        
        List<WeeklyScheduleResponseDTO> weeklyScheduleResponseDTOs = weeklyScheduleMapperTest.convertToListDTO(Arrays.asList(weeklySchedule));
        assertNotNull(weeklyScheduleResponseDTOs);

        List<WeeklyScheduleResponseDTO> result = weeklyScheduleService.getAllWeeklySchedules();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(weeklyScheduleRepository).findAll();
        verify(weeklyScheduleMapper).convertToListDTO(Arrays.asList(weeklySchedule));
    }
    
    @Test
    void testDeleteWeeklySchedule() {
        when(weeklyScheduleRepository.findById(anyLong())).thenReturn(Optional.of(weeklySchedule));

        assertDoesNotThrow(() -> weeklyScheduleService.deleteWeeklySchedule(1L));
        verify(weeklyScheduleRepository, times(1)).delete(any(WeeklySchedule.class));
    }

    @Test
    void testDeleteWeeklySchedule_ResourceNotFoundException() {
        // Arrange
        when(weeklyScheduleRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            weeklyScheduleService.deleteWeeklySchedule(1L);
        });
        verify(weeklyScheduleRepository, never()).delete(any());
    }

    @Test
    void testCreateWeeklySchedule() {
        when(specialistProfileRepository.findById(specialistProfileId)).thenReturn(Optional.of(specialistProfile));
        when(weeklyScheduleMapper.convertToEntity(weeklyScheduleRequestDTO)).thenReturn(weeklySchedule);
        when(weeklyScheduleRepository.save(any(WeeklySchedule.class))).thenReturn(weeklySchedule);
        when(weeklyScheduleMapper.convertToDTO(any(WeeklySchedule.class))).thenReturn(weeklyScheduleResponseDTO);
        // Configurar el comportamiento esperado del mock scheduleMapper
        when(scheduleMapper.craeteScheduleRequestDTO(any(LocalDate.class), any(DayOfWeek.class), any(LocalTime.class)))
                .thenReturn(scheduleRequestDTO);
        when(scheduleMapper.convertToEntity(scheduleRequestDTO)).thenReturn(schedule);
        
        WeeklySchedule weeklySchedule = weeklyScheduleMapperTest.convertToEntity(weeklyScheduleRequestDTO);
        assertNotNull(weeklySchedule);
        WeeklyScheduleResponseDTO weeklyScheduleResponseDTO = weeklyScheduleMapperTest.convertToDTO(weeklySchedule);
        assertNotNull(weeklyScheduleResponseDTO);
        
        Schedule schedule = scheduleMapperTest.convertToEntity(scheduleRequestDTO);
        assertNotNull(schedule);
        ScheduleRequestDTO scheduleRequestDTO = scheduleMapperTest.craeteScheduleRequestDTO(date, dayOfWeek, LocalTime.now());
        assertNotNull(scheduleRequestDTO);


        WeeklyScheduleResponseDTO responseDTO = weeklyScheduleService.createWeeklySchedule(specialistProfileId, weeklyScheduleRequestDTO);
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getWeeklyScheduleId());
    }

    @Test
    void testCreateWeeklySchedule_SpecialistProfileNotFound() {
        // Arrange
        when(specialistProfileRepository.findById(specialistProfileId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFound.class, () -> {
            weeklyScheduleService.createWeeklySchedule(specialistProfileId, weeklyScheduleRequestDTO);
        });
        verify(weeklyScheduleRepository, never()).save(any());
    }


    @Test
    void testGenerateSchedulesForDay() {
        // Configurar el comportamiento esperado del mock scheduleMapper
        when(scheduleMapper.craeteScheduleRequestDTO(any(LocalDate.class), any(DayOfWeek.class), any(LocalTime.class)))
                .thenReturn(scheduleRequestDTO);
        when(scheduleMapper.convertToEntity(scheduleRequestDTO)).thenReturn(schedule);
        
        // Llamar al método a probar
        List<Schedule> schedulesList = weeklyScheduleService.generateSchedulesForDay(date, dayOfWeek, weeklySchedule);

        // Verificar resultados
        assertNotNull(schedulesList);
        schedulesList.forEach(s -> assertEquals(date, s.getDate()));
    }


    
    @Test
    void testGetAllWeeklySchedules_Specialist() {
        when(specialistProfileRepository.findById(1L)).thenReturn(Optional.of(specialistProfile));
        when(weeklyScheduleMapper.convertToListDTO(specialistProfile.getWeeklySchedules()))
                .thenReturn(Arrays.asList(weeklyScheduleResponseDTO));

                
        List<WeeklyScheduleResponseDTO> weeklyScheduleResponseDTOs = weeklyScheduleMapperTest.convertToListDTO(specialistProfile.getWeeklySchedules());
        assertNotNull(weeklyScheduleResponseDTOs);

        List<WeeklyScheduleResponseDTO> result = weeklyScheduleService.
        getAllWeeklySchedules_Specialist(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(weeklyScheduleResponseDTO, result.get(0));
        verify(specialistProfileRepository).findById(1L);
        verify(weeklyScheduleMapper).convertToListDTO(specialistProfile.getWeeklySchedules());
    }

    @Test
    void testGetAllWeeklySchedules_Specialist_ProfileNotFound() {
        // Arrange
        Long specialistId = 1L;
        when(specialistProfileRepository.findById(specialistId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFound.class, () -> {
            weeklyScheduleService.getAllWeeklySchedules_Specialist(specialistId);
        });
    }
    @Test
    void testUpdateWeeklySchedule_Specialist_SuccessfulUpdate() {
        // Arrange
        Long specialistId = 1L;
        Long id = 1L;
        weeklySchedule.setStatus(WeeklyScheduleStatus.ACTIVE); // set initial status
        weeklyScheduleRequestDTO.setStatus(WeeklyScheduleStatus.ACTIVE.toString());

        when(specialistProfileRepository.findById(specialistId)).thenReturn(Optional.of(specialistProfile));
        when(weeklyScheduleRepository.save(weeklySchedule)).thenReturn(weeklySchedule);
        when(weeklyScheduleMapper.convertToDTO(weeklySchedule)).thenReturn(weeklyScheduleResponseDTO);
        
        WeeklyScheduleResponseDTO weeklyScheduleResponseDTO = weeklyScheduleMapperTest.convertToDTO(weeklySchedule);
        assertNotNull(weeklyScheduleResponseDTO);
        // Act
        WeeklyScheduleResponseDTO result = weeklyScheduleService.updateWeeklySchedule_Specialist(id, weeklyScheduleRequestDTO, specialistId);

        // Assert
        assertNotNull(result);
        assertEquals(WeeklyScheduleStatus.ACTIVE, weeklySchedule.getStatus());
    }

    
    @Test
    void testGetByWeeklyScheduleId_Specialist() {
        when(specialistProfileRepository.findById(1L)).thenReturn(Optional.of(specialistProfile));
        when(weeklyScheduleMapper.convertToDTO(weeklySchedule)).thenReturn(weeklyScheduleResponseDTO);
        
        WeeklyScheduleResponseDTO weeklyScheduleResponseDTO = weeklyScheduleMapperTest.convertToDTO(weeklySchedule);
        assertNotNull(weeklyScheduleResponseDTO);
        
        WeeklyScheduleResponseDTO result = weeklyScheduleService.getByWeeklyScheduleId_Specialist(1L,1L);
        assertNotNull(result);
    }
    @Test
    void testGetByWeeklyScheduleId_Specialist_SpecialistNotFound() {
        when(specialistProfileRepository.findById(specialistProfileId)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            weeklyScheduleService.getByWeeklyScheduleId_Specialist(1L, specialistProfileId);
        });

        verify(specialistProfileRepository).findById(specialistProfileId);
    }

}
