package com.nutrilife.fitnessservice.mapper;

import java.util.stream.Collectors;

import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.model.enums.WeeklyScheduleStatus;

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
    private ScheduleMapper scheduleMapper;

    public WeeklySchedule convertToEntity(WeeklyScheduleRequestDTO weeklyScheduleRequestDTO) {
        return modelMapper.map(weeklyScheduleRequestDTO, WeeklySchedule.class);
    }

    public WeeklyScheduleResponseDTO convertToDTO(WeeklySchedule weeklySchedule) {
        WeeklyScheduleResponseDTO responseDTO = modelMapper.map(weeklySchedule, WeeklyScheduleResponseDTO.class);
        if (weeklySchedule.getScheduleList() != null) {
            List<ScheduleResponseDTO> schedules = weeklySchedule.getScheduleList().stream()
                    .map(scheduleMapper::convertToDTO)
                    .collect(Collectors.toList());
            responseDTO.setSchedulesList(schedules);
        }
        return responseDTO;
    }

    public List<WeeklyScheduleResponseDTO> convertToListDTO(List<WeeklySchedule> weeklySchedules) {
        return weeklySchedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}