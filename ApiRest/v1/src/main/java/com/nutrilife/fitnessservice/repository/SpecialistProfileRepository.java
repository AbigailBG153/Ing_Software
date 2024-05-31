package com.nutrilife.fitnessservice.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
public interface SpecialistProfileRepository extends JpaRepository<SpecialistProfile, Long>{

    List<SpecialistProfile> findByName(String name);

    List<SpecialistProfile> findByOcuppation(String occupation);

    List<SpecialistProfile> findByAge(Integer age);

    List<SpecialistProfile> findByAgeRange(Integer minAge, Integer maxAge);

    List<SpecialistProfile> findByScore(Integer score);

    List<SpecialistProfile> findByScoreRange(Integer minScore, Integer maxScore);

}
