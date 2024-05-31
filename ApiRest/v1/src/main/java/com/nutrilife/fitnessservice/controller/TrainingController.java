package com.nutrilife.fitnessservice.controller;

import com.nutrilife.fitnessservice.model.dto.TrainingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingResponseDTO;
import com.nutrilife.fitnessservice.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping
    public ResponseEntity<List<TrainingResponseDTO>> getAllTrainings() {
        List<TrainingResponseDTO> trainings = trainingService.getAllTrainings();
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingResponseDTO> getTrainingById(@PathVariable Long id) {
        TrainingResponseDTO training = trainingService.getTrainingById(id);
        return new ResponseEntity<>(training, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainingResponseDTO> createTraining(@RequestBody TrainingRequestDTO trainingDTO) {
        TrainingResponseDTO training = trainingService.createTraining(trainingDTO);
        return new ResponseEntity<>(training, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
