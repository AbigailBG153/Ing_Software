package com.nutrilife.fitnessservice.model.entity;

import java.time.LocalDate;
import java.util.List;

import com.nutrilife.fitnessservice.model.enums.WeeklyScheduleStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="weekly_schedules")
public class WeeklySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_schedule_id")
    private Long weeklyScheduleId;

    @ManyToOne
    @JoinColumn(name = "specialist_profile_id")
    private SpecialistProfile specialistProfile;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WeeklyScheduleStatus status;

    @OneToMany(mappedBy = "weeklySchedule", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

}
