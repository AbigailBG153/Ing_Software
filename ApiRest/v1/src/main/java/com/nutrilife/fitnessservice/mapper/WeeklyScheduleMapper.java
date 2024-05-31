package com.nutrilife.fitnessservice.mapper;

import java.util.stream.Collectors;

import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Component
@AllArgsConstructor
public class WeeklyScheduleMapper {

    private final ModelMapper modelMapper;
    
    public ScheduleRequestDTO craeteScheduleRequestDTO(LocalDate date, DayOfWeek dayOfWeek,
    LocalTime startTime){
        ScheduleRequestDTO scheduleRequestDTO = new ScheduleRequestDTO();
        scheduleRequestDTO.setDayOfWeek(dayOfWeek.toString());
        scheduleRequestDTO.setDate(date);
        scheduleRequestDTO.setStartTime(startTime);
        scheduleRequestDTO.setEndTime(startTime.plusHours(1));
        scheduleRequestDTO.setStatus(ScheduleStatus.DISABLED.toString());
        return scheduleRequestDTO;
    } 
    public WeeklyScheduleResponseDTO convertToDTO(WeeklySchedule weeklySchedule) {
        WeeklyScheduleResponseDTO responseDTO = modelMapper.map(weeklySchedule, WeeklyScheduleResponseDTO.class);
        if (weeklySchedule.getScheduleList() != null) {
            List<ScheduleResponseDTO> schedules = weeklySchedule.getScheduleList().stream()
                    .map(this::convertScheduleToDTO)
                    .collect(Collectors.toList());
            responseDTO.setSchedules(schedules);
        }
        return responseDTO;
    }

    public ScheduleResponseDTO convertScheduleToDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleResponseDTO.class);
    }

    public List<WeeklyScheduleResponseDTO> convertToListDTO(List<WeeklySchedule> weeklySchedules) {
        return weeklySchedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    
}

