package com.nutrilife.fitnessservice.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nutrilife.fitnessservice.model.dto.MeetingReportDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import lombok.AllArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class MeetingMapper {
    private final ModelMapper modelMapper;
    public Meeting convertToEntity(MeetingRequestDTO meetingRequestDTO){
        return modelMapper.map(meetingRequestDTO , Meeting.class);
    }

    public MeetingResponseDTO convertToDTO(Meeting meeting){
        return modelMapper.map(meeting , MeetingResponseDTO.class);
    }

    public List<MeetingResponseDTO> convertToListDTO(List<Meeting> meetings) {
        return meetings.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public MeetingReportDTO convertMeetingReportDTO(Object[] meetingData){
        MeetingReportDTO reportDTO = new MeetingReportDTO();
        reportDTO.setDate((LocalDateTime) meetingData[0]);
        reportDTO.setMeetingCount((Long) meetingData[1]);
        return reportDTO; 
    }

}
