package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.nutrilife.fitnessservice.model.enums.MeetStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingResponseDTO {
    private Long meetingId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private MeetStatus meetStatus;
    private Long scheduleId; 
}
