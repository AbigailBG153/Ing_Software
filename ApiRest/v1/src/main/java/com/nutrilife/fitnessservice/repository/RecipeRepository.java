package com.nutrilife.fitnessservice.repository;

import com.nutrilife.fitnessservice.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNutritionalGoal(String nutritionalGoal);

    List<Recipe> findByTotalCaloriesBetween(float minCalories, float maxCalories);

    List<Recipe> findTop5ByOrderByScoreDesc();

    List<Recipe> findByType(String type);

    List<Recipe> findByIngredients_IdIn(List<Long> ingredientIds);

}
