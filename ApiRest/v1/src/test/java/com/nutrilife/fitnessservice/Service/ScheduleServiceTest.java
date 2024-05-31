package com.nutrilife.fitnessservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import com.nutrilife.fitnessservice.service.ScheduleService;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleService scheduleService;

    private Schedule schedule;
    private ScheduleResponseDTO scheduleResponseDTO;
    private ScheduleMapper scheduleMapperTest;

    @BeforeEach
    void setUp() {

        scheduleMapperTest = new ScheduleMapper(new ModelMapper());

        schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setStatus(ScheduleStatus.DISABLED);

        scheduleResponseDTO = new ScheduleResponseDTO();
        scheduleResponseDTO.setScheduleId(1L);
    }

    @Test
    void testGetAllSchedules() {
        when(scheduleRepository.findAll()).thenReturn(Arrays.asList(schedule));
        when(scheduleMapper.convertToListDTO(anyList())).thenReturn(Arrays.asList(scheduleResponseDTO));

        List<ScheduleResponseDTO> scheduleResponseDTOs = scheduleMapperTest.convertToListDTO(Arrays.asList(schedule));
        assertNotNull(scheduleResponseDTOs);

        List<ScheduleResponseDTO> result = scheduleService.getAllSchedules();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateScheduleStatus_Success() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);
        when(scheduleMapper.convertToDTO(any(Schedule.class))).thenReturn(scheduleResponseDTO);

        ScheduleResponseDTO scheduleResponseDTO = scheduleMapperTest.convertToDTO(schedule);
        assertNotNull(scheduleResponseDTO);

        ScheduleResponseDTO result = scheduleService.updateScheduleStatus(1L);
        assertNotNull(result);
        assertEquals(ScheduleStatus.ACTIVE, schedule.getStatus());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    void testUpdateScheduleStatus_NotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            scheduleService.updateScheduleStatus(1L);
        });

        assertEquals("Schedule not found with id 1", exception.getMessage());
    }

}
