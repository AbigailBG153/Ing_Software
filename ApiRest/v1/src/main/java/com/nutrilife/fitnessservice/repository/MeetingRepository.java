package com.nutrilife.fitnessservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nutrilife.fitnessservice.model.entity.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

}