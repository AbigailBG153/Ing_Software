package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MeetingResponseDTO{
    private Long meetId;
    private CustomerProfileResponseDTO customer;
    private SpecialistProfileResponseDTO specialist;
    private Specialist_ScheduleResponseDTO ScheduleId;
    private String typePlatform;
    private LocalDateTime registerDate;
}