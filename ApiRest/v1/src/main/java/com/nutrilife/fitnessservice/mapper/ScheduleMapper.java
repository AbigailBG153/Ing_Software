package com.nutrilife.fitnessservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleMapper {
    private final ModelMapper modelMapper;

    
    public Schedule convertToEntity(ScheduleRequestDTO scheduleRequestDTO) {
        return modelMapper.map(scheduleRequestDTO, Schedule.class);
    }

    public ScheduleResponseDTO convertToDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleResponseDTO.class);
    }

    public List<ScheduleResponseDTO> convertToListDTO(List<Schedule> schedules) {
        return schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Schedule> convertToListEntity(List<ScheduleRequestDTO> dtos) {
        return dtos.stream()
        .map(this::convertToEntity)
        .collect(Collectors.toList());
    }
}
