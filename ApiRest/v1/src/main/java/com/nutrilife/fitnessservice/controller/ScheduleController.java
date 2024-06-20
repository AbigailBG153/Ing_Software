package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<ScheduleResponseDTO> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.status(HttpStatus.OK).body(schedules);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateScheduleStatus(@PathVariable Long id) {
        ScheduleResponseDTO updatedSchedule = scheduleService.updateScheduleStatus(id);
        return ResponseEntity.ok(updatedSchedule);
    }

    @PutMapping("/updateAllTo")
    public ResponseEntity<String> updateAllSchedules() {
        scheduleService.updateAllSchedules();
        return ResponseEntity.ok("All schedules updated ");
    }

    
}
