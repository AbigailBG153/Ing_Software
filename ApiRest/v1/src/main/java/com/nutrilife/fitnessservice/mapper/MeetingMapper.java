package com.nutrilife.fitnessservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.enums.MeetStatus;

import lombok.AllArgsConstructor;
@Component
@AllArgsConstructor
public class MeetingMapper {
    private final ModelMapper modelMapper;

    public Meeting convertToEntity(MeetingRequestDTO meetingRequestDTO) {
        return modelMapper.map(meetingRequestDTO, Meeting.class);
    }

    public MeetingResponseDTO convertToDTO(Meeting meeting) {
        return modelMapper.map(meeting, MeetingResponseDTO.class);
    }

    public List<MeetingResponseDTO> convertToListDTO(List<Meeting> meetings) {
        return meetings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MeetingRequestDTO createMeetingRequestDTO(Schedule schedule){
        MeetingRequestDTO meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setDate(schedule.getDate());
        meetingRequestDTO.setStartTime(schedule.getStartTime());
        meetingRequestDTO.setEndTime(schedule.getEndTime());
        meetingRequestDTO.setStatus(MeetStatus.PENDING.toString());
        
        return  meetingRequestDTO;
    }
}
