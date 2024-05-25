package com.nutrilife.fitnessservice.model.dto;

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
    private String ocupation;
    private String email;
    private String password;
}
