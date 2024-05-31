package com.nutrilife.fitnessservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.nutrilife.fitnessservice.exception.ResourceNotFoundException;
import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.MeetingMapper;
import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.MeetStatus;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.repository.MeetingRepository;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final ScheduleRepository scheduleRepository;
    private final SpecialistProfileRepository specialistProfileRepository;
    private final ScheduleMapper scheduleMapper;
    private final MeetingMapper meetingMapper;

    public List<MeetingResponseDTO> getAllMeetings() {
        List<Meeting> meetings = meetingRepository.findAll();
        return meetingMapper.convertToListDTO(meetings);
    }

    public MeetingResponseDTO getMeetingById(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));
        return meetingMapper.convertToDTO(meeting);
    }


    public MeetingResponseDTO createMeeting(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));
        
        if (schedule.getStatus() != ScheduleStatus.ACTIVE) {
            throw new IllegalStateException("Cannot create a meeting for a schedule that is not ACTIVE.");
        }

        Meeting meeting = new Meeting();
        meeting.setDate(schedule.getDate());
        meeting.setStartTime(schedule.getStartTime());
        meeting.setEndTime(schedule.getEndTime());
        meeting.setStatus(MeetStatus.PENDING);
        meeting.setSchedule(schedule);
        Meeting savedMeeting = meetingRepository.save(meeting);
        MeetingResponseDTO responseDTO = meetingMapper.convertToDTO(savedMeeting);
        responseDTO.setScheduleId(scheduleId);
        schedule.setStatus(ScheduleStatus.OCCUPIED);
        scheduleRepository.save(schedule);

        return meetingMapper.convertToDTO(savedMeeting);
    }

    public MeetingResponseDTO updateMeeting(Long meetingId, MeetingRequestDTO requestDTO) {
        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + requestDTO.getScheduleId()));
        
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));
        
        meeting.setDate(schedule.getDate());
        meeting.setStartTime(schedule.getStartTime());
        meeting.setEndTime(schedule.getEndTime());
        meeting.setStatus(MeetStatus.valueOf(requestDTO.getStatus()));
        
        Meeting updatedMeeting = meetingRepository.save(meeting);
        return meetingMapper.convertToDTO(updatedMeeting);
    }

    public void deleteMeeting(Long meetingId) {
        
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with id: " + meetingId));

        Schedule schedule = meeting.getSchedule();
        schedule.setStatus(ScheduleStatus.ACTIVE);
        scheduleRepository.save(schedule);
        meetingRepository.delete(meeting);
        
    }

    /////funciones especiales
    public List<ScheduleResponseDTO> getDailySchedulesForCurrentWeek(Long specialistId) {
        SpecialistProfile specialistProfile = specialistProfileRepository.findById(specialistId)
            .orElseThrow(() -> new UserNotFound("Perfil de especialista no encontrado con el ID: " + specialistId));
        
        // Obtener la fecha de inicio de la semana actual (lunes)
        LocalDate startOfWeek = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        // Obtener la fecha de fin de la semana actual (domingo)
        LocalDate endOfWeek = startOfWeek.plusDays(6);
    
        WeeklySchedule weeklySchedule = specialistProfile.getWeeklySchedules().stream()
        .filter(ws -> !ws.getStartDate().isBefore(startOfWeek) && !ws.getEndDate().isAfter(endOfWeek))
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("Weekly schedule not found for the current week"));

        List<Schedule> dailySchedules = weeklySchedule.getScheduleList().stream()
        .filter(schedule -> !schedule.getDate().isBefore(startOfWeek) && !schedule.getDate().isAfter(endOfWeek))
        .sorted(Comparator
                .comparing((Schedule s) -> s.getDate().getDayOfWeek())
                .thenComparing(Schedule::getStartTime))
        .collect(Collectors.toList());
    
        return scheduleMapper.convertToListDTO(dailySchedules);
    }


}
