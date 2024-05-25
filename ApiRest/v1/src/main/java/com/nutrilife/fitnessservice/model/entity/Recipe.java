package com.nutrilife.fitnessservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "nutritional_goal")
    private String nutritionalGoal;

    @Column(name = "total_calories")
    private float totalCalories;

    @Column(name = "image")
    private String image;

    @Column(name = "score")
    private float score;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    public List<String> getIngredientNames() {
        return this.ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.toList());
    }
}
