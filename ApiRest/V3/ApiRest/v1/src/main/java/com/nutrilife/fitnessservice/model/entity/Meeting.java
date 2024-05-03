package com.nutrilife.fitnessservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Meetings")
public class Meeting{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="meet_id")
    private Long meetId;

    @ManyToOne
    @JoinColumn(name = "meet-cust_id", nullable = false)
    private CustomerProfile custId;

    @ManyToOne
    @JoinColumn(name = "meet-spec_id", nullable = false)
    private SpecialistProfile  specId;

    @ManyToOne
    @JoinColumn(name = "meet-sche_id", nullable = false)
    private Specialist_Schedule ScheduleId;
    
    @Column(name = "meeting_name", nullable = false)
    private String meetingName;

    @Column(name = "type_platform", nullable = false)
    private String typePlatform;

    @Column(name = "meeting_url", nullable = false)
    private String meetingUrl;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;
    
    @Column(name = "register_date", nullable = false)
    private LocalDateTime registerDate;


}