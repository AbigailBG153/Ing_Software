package com.nutrilife.fitnessservice.model.dto;

//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScheduleResponseDTO {
    private Long scheduleId;
    private String dayOfWeek;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ScheduleStatus status;
    private MeetingResponseDTO meeting;
}