package com.nutrilife.fitnessservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
