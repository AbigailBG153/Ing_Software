package com.nutrilife.fitnessservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.repository.MeetingRepository;
import com.nutrilife.fitnessservice.service.MeetingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/meetings")
@RequiredArgsConstructor
public class MeetingController {

    @Autowired
    private MeetingService meetingService;
    
    @GetMapping("/allMeetings")
    public ResponseEntity<List<MeetingResponseDTO>> getAllMeetings() {
        List<MeetingResponseDTO> meetings = meetingService.getAllMeetings();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping()
    public ResponseEntity<MeetingResponseDTO> getMeetingById(@RequestParam("MeetingId") String meetingId) {
        Long id = Long.parseLong(meetingId);
        MeetingResponseDTO meeting = meetingService.getMeetingById(id);
        return ResponseEntity.ok(meeting);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<MeetingResponseDTO>> getMeetingsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<MeetingResponseDTO> meetings = meetingService.getMeetingByCustomerId(customerId);
        return ResponseEntity.ok(meetings);
    }


    @PostMapping("/customer/{customerId}")
    public ResponseEntity<MeetingResponseDTO> createMeeting(@RequestParam("scheduleId") Long scheduleId,
    @PathVariable("customerId") Long customerId) {
        
        MeetingResponseDTO createdMeeting = meetingService.createMeeting(scheduleId,customerId);
        return new ResponseEntity<>(createdMeeting, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MeetingResponseDTO> updateMeeting(@RequestParam("meetingId") String meetingId) {
        Long id = Long.parseLong(meetingId);
        
        MeetingResponseDTO updatedMeeting = meetingService.updateMeeting(id);
        return ResponseEntity.ok(updatedMeeting);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMeeting(@RequestParam("meetingId") Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialist/{specialistId}/currentWeek")
    public ResponseEntity<List<ScheduleResponseDTO>> getDailySchedulesForCurrentWeek(@PathVariable Long specialistId) {
        List<ScheduleResponseDTO> dailySchedules = meetingService.getDailySchedulesForCurrentWeek(specialistId);
        return ResponseEntity.ok(dailySchedules);
        
    } 
}
