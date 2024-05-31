package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScheduleResponseDTO {
    private Long weeklyScheduleId;
    private Long specialistId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private List<ScheduleResponseDTO> schedulesList;
}