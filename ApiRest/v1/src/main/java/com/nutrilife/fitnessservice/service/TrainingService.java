package com.nutrilife.fitnessservice.service;

import com.nutrilife.fitnessservice.exception.TrainingNotFoundException;
import com.nutrilife.fitnessservice.exception.TrainingAlreadyExistsException;
import com.nutrilife.fitnessservice.mapper.TrainingMapper;
import com.nutrilife.fitnessservice.model.dto.*;
import com.nutrilife.fitnessservice.model.entity.Training;
import com.nutrilife.fitnessservice.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Transactional
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO) {
        if (trainingRepository.existsByName(trainingRequestDTO.getName())) {
            throw new TrainingAlreadyExistsException("Training with the same name already exists");
        }
        Training training = trainingMapper.convertToEntity(trainingRequestDTO);
        trainingRepository.save(training);
        return trainingMapper.convertToDTO(training);
    }

    @Transactional(readOnly = true)
    public TrainingResponseDTO getTrainingById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException("Training not found with id: " + id));
        return trainingMapper.convertToDTO(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getAllTrainings() {
        List<Training> trainings = trainingRepository.findAll();
        return trainings.stream()
                .map(trainingMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TrainingResponseDTO updateTraining(Long id, TrainingUpdateDTO trainingUpdateDTO) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new TrainingNotFoundException("Training not found with id: " + id));
        trainingMapper.updateEntityFromDTO(trainingUpdateDTO, training);
        return trainingMapper.convertToDTO(training);
    }

    @Transactional
    public void deleteTraining(Long id) {
        if (!trainingRepository.existsById(id)) {
            throw new TrainingNotFoundException("Training not found with id: " + id);
        }
        trainingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TrainingResponseDTO> getTrainingsByExerciseType(String exerciseType) {
        List<Training> trainings = trainingRepository.findByExerciseType(exerciseType);
        return trainings.stream()
                .map(trainingMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TrainingReportDTO> generateTrainingReport(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        List<Object[]> trainingCounts = trainingRepository.getTrainingCountByDateRange(startDate, endDate);
        return trainingCounts.stream()
                .map(trainingMapper::convertTrainingReportDTO)
                .collect(Collectors.toList());
    }
}