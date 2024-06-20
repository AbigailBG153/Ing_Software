package com.nutrilife.fitnessservice.service;

import org.springframework.stereotype.Service;
import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.ScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;

import java.util.List;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;


    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return scheduleMapper.convertToListDTO(schedules);
    }

    @Transactional
    public ScheduleResponseDTO updateScheduleStatus(Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            schedule.setStatus(ScheduleStatus.ACTIVE);
            Schedule updatedSchedule = scheduleRepository.save(schedule);
            return scheduleMapper.convertToDTO(updatedSchedule);
        } else {
            throw new RuntimeException("Schedule not found with id " + scheduleId);
        }
    }
    @Transactional
    public void updateAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        for (Schedule schedule : schedules) {
            schedule.setStatus(ScheduleStatus.DISABLED);
        }
    }

}
