package com.nutrilife.fitnessservice.model.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "specProfiles")
public class SpecialistProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="spec_id")
    private Long specId;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "age", nullable = false)
    private Integer age;
    @Column(name = "occupation")
    private String occupation;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy = "specialistProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklySchedule> weeklySchedules;
}