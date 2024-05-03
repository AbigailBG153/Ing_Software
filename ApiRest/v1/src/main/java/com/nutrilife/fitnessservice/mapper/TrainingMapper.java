package com.nutrilife.fitnessservice.mapper;

import com.nutrilife.fitnessservice.model.dto.TrainingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Training;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    private final ModelMapper modelMapper;

    public TrainingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Training convertToEntity(TrainingRequestDTO trainingRequestDTO) {
        return modelMapper.map(trainingRequestDTO, Training.class);
    }

    public TrainingResponseDTO convertToDTO(Training training) {
        return modelMapper.map(training, TrainingResponseDTO.class);
    }
}

