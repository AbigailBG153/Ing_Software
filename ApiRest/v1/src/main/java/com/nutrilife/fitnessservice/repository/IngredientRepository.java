package com.nutrilife.fitnessservice.repository;

import com.nutrilife.fitnessservice.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameIgnoreCase(String name);
}
