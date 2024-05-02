package com.nutrilife.fitnessservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrilife.fitnessservice.model.entity.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long>{
    
    @Query("SELECT cp FROM CustomerProfile cp WHERE cp.name LIKE CONCAT('%', :name, '%')")
    Optional<CustomerProfile> getCustomerProfileByName(@Param("name") String name);

    @Query("SELECT cp FROM CustomerProfile cp WHERE cp.user.id =:userId")
    Optional<CustomerProfile> findByUserId(@Param("userId") Long userId);
}
