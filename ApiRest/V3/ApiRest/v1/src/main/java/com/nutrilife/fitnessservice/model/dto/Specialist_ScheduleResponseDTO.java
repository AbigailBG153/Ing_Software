package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Specialist_ScheduleResponseDTO {
    private Long ScheduleId;
    private SpecialistProfileResponseDTO specialist;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String state;
}
