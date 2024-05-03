package com.nutrilife.fitnessservice.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "calories")
    private float calories;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
