package com.nutrilife.fitnessservice.mapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;

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

    public ScheduleRequestDTO craeteScheduleRequestDTO(LocalDate date, DayOfWeek dayOfWeek,
            LocalTime startTime) {
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setDayOfWeek(dayOfWeek.toString());
        scheduleRequestDTO.setDate(date);
        scheduleRequestDTO.setStartTime(startTime);
        scheduleRequestDTO.setEndTime(startTime.plusHours(1));
        scheduleRequestDTO.setStatus(ScheduleStatus.DISABLED.toString());
        return scheduleRequestDTO;
    }
}