package com.nutrilife.fitnessservice.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleResponseDTO;
import com.nutrilife.fitnessservice.service.WeeklyScheduleService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/specialist/{specialistId}/weekly-schedules")
@AllArgsConstructor
public class WeeklyScheduleController {

    private final WeeklyScheduleService weeklyScheduleService;
    
    @PostMapping
    public ResponseEntity<WeeklyScheduleResponseDTO> createWeeklySchedule(
    @PathVariable("specialistId") Long specialistId,
    @RequestBody WeeklyScheduleRequestDTO requestDTO) {
        WeeklyScheduleResponseDTO responseDTO = weeklyScheduleService.createWeeklySchedule(specialistId,requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @GetMapping
    public ResponseEntity<List<WeeklyScheduleResponseDTO>> getAllWeeklySchedules_Specialist(@PathVariable("specialistId") Long specialistId) {
        List<WeeklyScheduleResponseDTO> responseDTOs = weeklyScheduleService.getAllWeeklySchedules_Specialist(specialistId);
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeeklyScheduleResponseDTO> updateWeeklySchedule_Specialist(
        @PathVariable Long id, @RequestBody WeeklyScheduleRequestDTO requestDTO,
        @PathVariable("specialistId") Long specialistId) {
        WeeklyScheduleResponseDTO responseDTO = weeklyScheduleService.updateWeeklySchedule_Specialist(id, requestDTO,specialistId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeeklySchedule(@PathVariable Long id) {
        weeklyScheduleService.deleteWeeklySchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/one-weekly-schedule/{id}")
    public ResponseEntity<WeeklyScheduleResponseDTO> getByWeeklyScheduleId_Specialist(
        @PathVariable Long id,
        @PathVariable("specialistId") Long specialistId) {
        WeeklyScheduleResponseDTO responseDTOs = weeklyScheduleService.getByWeeklyScheduleId_Specialist(id,specialistId);
        return ResponseEntity.ok(responseDTOs);
    }
}

