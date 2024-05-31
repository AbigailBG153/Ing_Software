package com.nutrilife.fitnessservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;

public interface SpecialistProfileRepository extends JpaRepository<SpecialistProfile, Long> {

    @Query("SELECT sp FROM SpecialistProfile sp WHERE sp.user.id =:userId")
    Optional<SpecialistProfile> findByUserId(@Param("userId") Long userId);

    @Query("SELECT sp FROM SpecialistProfile sp WHERE sp.name LIKE CONCAT('%', :name, '%')")
    Optional<SpecialistProfile> getSpecialistProfileByName(@Param("name") String name);

    List<SpecialistProfile> findByName(String name);

    List<SpecialistProfile> findByOccupation(String occupation);

    List<SpecialistProfile> findByAge(Integer age);

    List<SpecialistProfile> findByAgeRange(Integer minAge, Integer maxAge);

    List<SpecialistProfile> findByScore(Integer score);

    List<SpecialistProfile> findByScoreRange(Integer minScore, Integer maxScore);
}
