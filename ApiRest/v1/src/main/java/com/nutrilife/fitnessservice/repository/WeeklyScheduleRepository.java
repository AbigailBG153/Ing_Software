package com.nutrilife.fitnessservice.repository;


import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;

@Repository
public interface WeeklyScheduleRepository extends JpaRepository<WeeklySchedule, Long> {
    Optional<WeeklySchedule> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);

}

