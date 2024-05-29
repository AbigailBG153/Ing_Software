package com.nutrilife.fitnessservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;

public interface SpecialistProfileRepository extends JpaRepository<SpecialistProfile, Long>{

    @Query("SELECT sp FROM SpecialistProfile sp WHERE sp.Spe_id =:Spe_id")
    Optional<SpecialistProfile> getSpecialistProfileById(@Param("Spe_id") Long Spe_id);

    @Query("SELECT sp FROM SpecialistProfile sp WHERE sp.Spe_name LIKE CONCAT('%', :Spe_name, '%')")
    Optional<List<SpecialistProfile>> getSpecialistProfileByName(@Param("Spe_name") String Spe_name);
}
