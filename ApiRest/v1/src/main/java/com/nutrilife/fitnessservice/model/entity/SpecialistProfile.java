package com.nutrilife.fitnessservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    //@Lob
    //@Column(name = "stud_certificate", nullable = false)
    //private byte[] studCertificate;
    //@Lob
    //@Column(name = "cv", nullable = false)
    //private byte[] cv;
    @Column(name = "score")
    private Integer score;
    @Column(name = "ocupation")
    private String ocupation;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}