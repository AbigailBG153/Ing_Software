package com.nutrilife.fitnessservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrilife.fitnessservice.model.entity.User;

public interface UserRespository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.email =:email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email =:email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email =:email")
    Optional<User> countEmail(@Param("email") String email);
}
