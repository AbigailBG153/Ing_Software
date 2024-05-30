package com.nutrilife.fitnessservice.repository;

import com.nutrilife.fitnessservice.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNutritionalGoal(String nutritionalGoal);

    List<Recipe> findByTotalCaloriesBetween(float minCalories, float maxCalories);

    List<Recipe> findTop5ByOrderByScoreDesc();

    List<Recipe> findByType(String type);

    List<Recipe> findByIngredients_IdIn(List<Long> ingredientIds);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.ingredients i WHERE i.id IN :ingredientIds AND r.score >= 3.5")
    List<Recipe> findPopularRecipesByIngredients(@Param("ingredientIds") List<Long> ingredientIds);

}
