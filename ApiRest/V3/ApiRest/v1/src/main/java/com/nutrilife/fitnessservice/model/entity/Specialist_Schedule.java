package com.nutrilife.fitnessservice.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Specialist_Schedules")
public class Specialist_Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="schedule_id")
    private Long ScheduleId;

    @ManyToOne
    @JoinColumn(name = "meet-spec_id", nullable = false)
    private SpecialistProfile  specId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "state", nullable = false)
    private LocalDateTime state;

}
