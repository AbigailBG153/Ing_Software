package com.nutrilife.fitnessservice.repository;

import com.nutrilife.fitnessservice.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByExerciseType(String exerciseType);

    boolean existsByName(String name);

    @Query("SELECT COUNT(t), t.date FROM Training t WHERE t.date BETWEEN :startDate AND :endDate GROUP BY t.date")
    List<Object[]> getTrainingCountByDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}