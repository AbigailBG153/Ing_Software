package com.nutrilife.fitnessservice.services;

import com.nutrilife.fitnessservice.exception.TrainingNotFoundException;
import com.nutrilife.fitnessservice.mapper.TrainingMapper;
import com.nutrilife.fitnessservice.model.dto.TrainingReportDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingResponseDTO;
import com.nutrilife.fitnessservice.model.dto.TrainingUpdateDTO;
import com.nutrilife.fitnessservice.model.entity.Training;
import com.nutrilife.fitnessservice.repository.TrainingRepository;
import com.nutrilife.fitnessservice.service.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    public void testCreateTraining_Successful() {
        // Arrange
        TrainingRequestDTO requestDTO = new TrainingRequestDTO();
        requestDTO.setName("Training 1");
        requestDTO.setDescription("Test training");
        requestDTO.setExerciseType("Cardio");
        requestDTO.setPhysicalGoal("Weight Loss");
        requestDTO.setDuration(60.0f);
        requestDTO.setKCalories(500.0f);
        requestDTO.setVideo("video_url");

        Training training = new Training();
        training.setId(1L);

        when(trainingMapper.convertToEntity(requestDTO)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.convertToDTO(training)).thenReturn(new TrainingResponseDTO());

        // Act
        TrainingResponseDTO result = trainingService.createTraining(requestDTO);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testGetTrainingById_NotFound() {
        // Arrange
        Long id = 1L;
        when(trainingRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TrainingNotFoundException.class, () -> trainingService.getTrainingById(id));
    }

    @Test
    public void testGetTrainingById_Found() {
        // Arrange
        Long id = 1L;
        Training training = new Training();
        training.setId(id);
        when(trainingRepository.findById(id)).thenReturn(Optional.of(training));
        when(trainingMapper.convertToDTO(training)).thenReturn(new TrainingResponseDTO());

        // Act
        TrainingResponseDTO result = trainingService.getTrainingById(id);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testUpdateTraining_NotFound() {
        // Arrange
        Long id = 1L;
        TrainingUpdateDTO updateDTO = new TrainingUpdateDTO();

        when(trainingRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TrainingNotFoundException.class, () -> trainingService.updateTraining(id, updateDTO));
    }

    @Test
    public void testUpdateTraining_Successful() {
        // Arrange
        Long id = 1L;
        TrainingUpdateDTO updateDTO = new TrainingUpdateDTO();
        updateDTO.setName("Updated Training");

        Training training = new Training();
        training.setId(id);

        when(trainingRepository.findById(id)).thenReturn(Optional.of(training));
        doNothing().when(trainingMapper).updateEntityFromDTO(updateDTO, training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.convertToDTO(training)).thenReturn(new TrainingResponseDTO());

        // Act
        TrainingResponseDTO result = trainingService.updateTraining(id, updateDTO);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testDeleteTraining_NotFound() {
        // Arrange
        Long id = 1L;
        when(trainingRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(TrainingNotFoundException.class, () -> trainingService.deleteTraining(id));
    }

    @Test
    public void testDeleteTraining_Successful() {
        // Arrange
        Long id = 1L;
        when(trainingRepository.existsById(id)).thenReturn(true);
        doNothing().when(trainingRepository).deleteById(id);

        // Act
        trainingService.deleteTraining(id);

        // Assert
        verify(trainingRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGenerateTrainingReport() {
        // Arrange
        String startDateStr = "2024-01-01";
        String endDateStr = "2024-01-31";
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        List<Object[]> trainingCounts = List.of(
                new Object[]{5L, LocalDate.parse("2024-01-01")},
                new Object[]{3L, LocalDate.parse("2024-01-15")}
        );

        List<TrainingReportDTO> expectedReport = trainingCounts.stream()
                .map(data -> new TrainingReportDTO((LocalDate) data[1], (Long) data[0]))
                .toList();

        when(trainingRepository.getTrainingCountByDateRange(startDate, endDate)).thenReturn(trainingCounts);

        // Act
        List<TrainingReportDTO> result = trainingService.generateTrainingReport(startDateStr, endDateStr);

        // Assert
        assertEquals(expectedReport.size(), result.size());
    }
}
