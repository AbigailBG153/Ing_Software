package com.nutrilife.fitnessservice.repository;

import com.nutrilife.fitnessservice.model.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    // Puedes agregar métodos adicionales de consulta según sea necesario
}
