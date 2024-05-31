package com.nutrilife.fitnessservice.service;

import com.nutrilife.fitnessservice.exception.TrainingNotFoundException;
import com.nutrilife.fitnessservice.mapper.TrainingMapper;
import com.nutrilife.fitnessservice.model.dto.TrainingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Training;
import com.nutrilife.fitnessservice.repository.TrainingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @Transactional
    public TrainingResponseDTO createTraining(TrainingRequestDTO trainingRequestDTO) {
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
    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }
}
