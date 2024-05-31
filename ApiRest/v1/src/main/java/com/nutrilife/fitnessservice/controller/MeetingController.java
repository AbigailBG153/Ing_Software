package com.nutrilife.fitnessservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.service.MeetingService;
import com.nutrilife.fitnessservice.service.WeeklyScheduleService;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<MeetingResponseDTO>> getAllMeetings() {
        List<MeetingResponseDTO> meetings = meetingService.getAllMeetings();
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponseDTO> getMeetingById(@PathVariable Long meetingId) {
        MeetingResponseDTO meeting = meetingService.getMeetingById(meetingId);
        return ResponseEntity.ok(meeting);
    }

    @PostMapping("/schedule/{scheduleId}")
    public ResponseEntity<MeetingResponseDTO> createMeeting(@PathVariable Long scheduleId) {
        MeetingResponseDTO createdMeeting = meetingService.createMeeting(scheduleId);
        return ResponseEntity.ok(createdMeeting);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<MeetingResponseDTO> updateMeeting(@PathVariable Long meetingId, @RequestBody MeetingRequestDTO requestDTO) {
        MeetingResponseDTO updatedMeeting = meetingService.updateMeeting(meetingId, requestDTO);
        return ResponseEntity.ok(updatedMeeting);
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<Void> deleteMeeting(@PathVariable Long meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialist/{specialistId}/currentWeek")
    public ResponseEntity<List<ScheduleResponseDTO>> getDailySchedulesForCurrentWeek(@PathVariable("specialistId") Long specialistId) {
        List<ScheduleResponseDTO> dailySchedules = meetingService.getDailySchedulesForCurrentWeek(specialistId);
        return ResponseEntity.ok(dailySchedules);
        
    } 
}
