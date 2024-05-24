package com.nutrilife.fitnessservice.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.nutrilife.fitnessservice.exception.ResourceNotFoundException;
import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.mapper.WeeklyScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleRequestDTO;
import com.nutrilife.fitnessservice.model.dto.WeeklyScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.model.enums.WeeklyScheduleStatus;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;
import com.nutrilife.fitnessservice.repository.WeeklyScheduleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WeeklyScheduleService {

    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    private final SpecialistProfileRepository specialistProfileRepository;
    
    ///CRUD
    
    public List<WeeklyScheduleResponseDTO> getAllWeeklySchedules() {
        List<WeeklySchedule> weeklySchedules = weeklyScheduleRepository.findAll();
        return weeklyScheduleMapper.convertToListDTO(weeklySchedules);
    }

    public WeeklyScheduleResponseDTO updateWeeklySchedule(Long id, WeeklyScheduleRequestDTO requestDTO) {
        WeeklySchedule weeklySchedule = weeklyScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WeeklySchedule not found with id: " + id));
        
        // Verificar si el estado es "TERMINADO"
        if (weeklySchedule.getStatus() == WeeklyScheduleStatus.DISABLED) {
            throw new IllegalStateException("Cannot update schedule when status is DISABLED");
        }
        
        // Guardar los cambios en la base de datos
        WeeklySchedule updatedWeeklySchedule = weeklyScheduleRepository.save(weeklySchedule);
        return weeklyScheduleMapper.convertToDTO(updatedWeeklySchedule);
    }

    public void deleteWeeklySchedule(Long id) {
        WeeklySchedule weeklySchedule = weeklyScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WeeklySchedule not found with id: " + id));

        // Eliminar el horario semanal (esto también eliminará en cascada la lista de horarios asociada)
        weeklyScheduleRepository.delete(weeklySchedule);
    }

    ///FUNCIONES ESPECIALES PARA ESPECIALISTA
    public WeeklyScheduleResponseDTO createWeeklySchedule(Long specialistProfileId, WeeklyScheduleRequestDTO requestDTO) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(specialistProfileId)
            .orElseThrow(() -> new UserNotFound("Perfil de especialista no encontrado con el ID: " + specialistProfileId));

        
        // Crear un horario semanal con la fecha de inicio y final proporcionadas
        WeeklySchedule weeklySchedule = new WeeklySchedule();
        weeklySchedule.setStartDate(requestDTO.getStartDate());
        weeklySchedule.setEndDate(requestDTO.getEndDate());
        weeklySchedule.setStatus(WeeklyScheduleStatus.DISABLED); // Establecer el estado predeterminado
        weeklySchedule.setSpecialistProfile(specialistProfile);

        // Guardar el horario semanal en la base de datos primero
        WeeklySchedule savedWeeklySchedule = weeklyScheduleRepository.save(weeklySchedule);
        
        WeeklyScheduleResponseDTO responseDTO = weeklyScheduleMapper.convertToDTO(savedWeeklySchedule);
        responseDTO.setSpecialistId(specialistProfileId);

        List<Schedule> schedulesList = new ArrayList<>();
        LocalDate currentDate = requestDTO.getStartDate();
        
        while (!currentDate.isAfter(weeklySchedule.getEndDate())) {
            schedulesList.addAll(generateSchedulesForDay(currentDate, currentDate.getDayOfWeek(), weeklySchedule));
            currentDate = currentDate.plusDays(1);
        }
        // Asignar la lista de horarios creados al horario semanal
        savedWeeklySchedule.setScheduleList(schedulesList);
    
        // Guardar los horarios diarios en la base de datos
        scheduleRepository.saveAll(schedulesList);
    
        // Guardar nuevamente el WeeklySchedule con la lista de Schedule asociada
        savedWeeklySchedule = weeklyScheduleRepository.save(savedWeeklySchedule);
    
        // Devolver el DTO del horario semanal
        return weeklyScheduleMapper.convertToDTO(savedWeeklySchedule);
    }
    
    private List<Schedule> generateSchedulesForDay(LocalDate date, DayOfWeek dayOfWeek, WeeklySchedule weeklySchedule) {
        List<Schedule> schedules = new ArrayList<>();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(22, 0); // 10:00 PM

        while (startTime.isBefore(endTime)) {
            Schedule schedule = scheduleMapper.convertToEntity(weeklyScheduleMapper.craeteScheduleRequestDTO(date, dayOfWeek, startTime));
            schedule.setWeeklySchedule(weeklySchedule);
        
            schedules.add(schedule);

            startTime = startTime.plusHours(1);
        }

        return schedules;
    }
    
    public List<WeeklyScheduleResponseDTO> getAllWeeklySchedules_Specialist(Long specialistId){
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(specialistId)
            .orElseThrow(() -> new UserNotFound("Perfil de especialista no encontrado con el ID: " + specialistId));
        List<WeeklySchedule> weeklySchedules = specialistProfile.getWeeklySchedules();
        weeklySchedules.forEach(weeklySchedule -> weeklySchedule.getScheduleList().sort(Comparator
        .comparing((Schedule s) -> s.getDate().getDayOfWeek())
        .thenComparing(Schedule::getStartTime)));
        return weeklyScheduleMapper.convertToListDTO(weeklySchedules);
    }
    

    public WeeklyScheduleResponseDTO updateWeeklySchedule_Specialist(Long id, WeeklyScheduleRequestDTO requestDTO,Long specialistId) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(specialistId)
        .orElseThrow(() -> new UserNotFound("Perfil de especialista no encontrado con el ID: " + specialistId));

        WeeklySchedule weeklySchedule = specialistProfile.getWeeklySchedules().stream()
        .filter(ws -> ws.getWeeklyScheduleId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Weekly schedule not found with id: " + id));

        // Verificar si el estado es "TERMINADO"
        if (weeklySchedule.getStatus() == WeeklyScheduleStatus.COMPLETED) {
            throw new IllegalStateException("Cannot update schedule when status is DISABLED");
        }
        weeklySchedule.setStatus(WeeklyScheduleStatus.valueOf(requestDTO.getStatus()));
        // Guardar los cambios en la base de datos
        WeeklySchedule updatedWeeklySchedule = weeklyScheduleRepository.save(weeklySchedule);
        return weeklyScheduleMapper.convertToDTO(updatedWeeklySchedule);
    }

    
    public WeeklyScheduleResponseDTO getByWeeklyScheduleId_Specialist(Long id,Long specialistId) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(specialistId)
        .orElseThrow(() -> new UserNotFound("Perfil de especialista no encontrado con el ID: " + specialistId));

        WeeklySchedule weeklySchedule = specialistProfile.getWeeklySchedules().stream()
        .filter(ws -> ws.getWeeklyScheduleId().equals(id))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Weekly schedule not found with id: " + id));
        WeeklySchedule weeklyScheduleId = weeklySchedule;
        weeklyScheduleId.getScheduleList().sort(Comparator
        .comparing((Schedule s) -> s.getDate().getDayOfWeek())
        .thenComparing(Schedule::getStartTime));;

        return weeklyScheduleMapper.convertToDTO(weeklyScheduleId);
    }

}


