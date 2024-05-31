package com.nutrilife.fitnessservice.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.Specialist_ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.Specialist_ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Specialist_Schedule;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class Specialist_ScheduleMapper {
    private final ModelMapper modelMapper;
    public Specialist_Schedule convertToEntity(Specialist_ScheduleRequestDTO specialist_ScheduleRequestDTO){
        return modelMapper.map(specialist_ScheduleRequestDTO , Specialist_Schedule.class);
    }

    public Specialist_ScheduleResponseDTO convertToDTO(Specialist_Schedule specialist_Schedule){
        return modelMapper.map(specialist_Schedule , Specialist_ScheduleResponseDTO.class);
    }

    public List<Specialist_ScheduleResponseDTO> convertToListDTO(List<Specialist_Schedule> meetings) {
        return meetings.stream()
                .map(this::convertToDTO)
                .toList();
    }
}
