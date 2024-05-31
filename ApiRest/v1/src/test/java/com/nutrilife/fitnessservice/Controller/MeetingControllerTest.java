package com.nutrilife.fitnessservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.model.entity.Schedule;

import com.nutrilife.fitnessservice.model.enums.MeetStatus;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;

import com.nutrilife.fitnessservice.repository.MeetingRepository;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MeetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private Schedule schedule;
    private Meeting meeting;

    @BeforeEach
    public void setUp() {

        schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setStatus(ScheduleStatus.ACTIVE);
        schedule.setDate(LocalDate.of(2024, 5, 27));
        schedule.setDayOfWeek(LocalDate.of(2024, 5, 27).getDayOfWeek().toString());
        schedule.setStartTime(LocalTime.of(11, 0));
        schedule.setEndTime(LocalTime.of(12, 0));
        schedule.setWeeklySchedule(null);
        scheduleRepository.save(schedule);

    }

    @Test
    void testGetAllMeetings() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/meetings/allMeetings", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetMeetingsByCustomerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/meetings/customer/{customerId}", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetMeetingById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/meetings")
                .param("MeetingId", "15"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateMeeting() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/meetings/customer/{customerId}", "1")
                .param("scheduleId", "1"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateMeeting() throws Exception {
        MeetingRequestDTO meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setStatus(MeetStatus.COMPLETED.toString());
        mockMvc.perform(MockMvcRequestBuilders.put("/meetings")
                .param("meetingId", "16")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(meetingRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteMeeting() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/meetings")
                .param("meetingId", "17"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    // Método auxiliar para convertir objetos a JSON
    private String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Registrar el módulo para soporte de Java 8 Date/Time
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
