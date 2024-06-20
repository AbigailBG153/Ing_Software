package com.nutrilife.fitnessservice.model.dto;


import java.util.List;

import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistProfileResponseDTO {
    private Long specId;
    private String name;
    private String phoneNumber;
    private Integer age;
    private Integer score;
    private String occupation;
    private String email;
    
}
