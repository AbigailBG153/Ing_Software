package com.nutrilife.fitnessservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerProfileResponseDTO {
    private Long custId;
    private String name;
    private String phoneNumber;
    private Integer age;
    private Float weight;
    private Float height;
    private String alergies;
    private String dietType;
    private String dietRestriction;
    private String email;
}
