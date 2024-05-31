package com.nutrilife.fitnessservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "exercise_type")
    private String exerciseType;

    @Column(name = "physical_goal")
    private String physicalGoal;

    @Column(name = "duration")
    private float duration;

    @Column(name = "k_calories")
    private float kCalories;

    @Column(name = "video")
    private String video;

    @Column(name = "date")
    private LocalDate date;
}
