package com.nutrilife.fitnessservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingUpdateDTO {

    private String name;
    private String description;
    private String exerciseType;
    private String physicalGoal;
    private float duration;
    private float kCalories;
    private String video;
    private int qualification;
}
