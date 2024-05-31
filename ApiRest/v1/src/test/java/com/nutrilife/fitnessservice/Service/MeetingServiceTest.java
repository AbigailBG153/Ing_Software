package com.nutrilife.fitnessservice.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.nutrilife.fitnessservice.exception.ResourceNotFoundException;
import com.nutrilife.fitnessservice.exception.UserNotFound;
import com.nutrilife.fitnessservice.mapper.MeetingMapper;
import com.nutrilife.fitnessservice.mapper.ScheduleMapper;
import com.nutrilife.fitnessservice.mapper.WeeklyScheduleMapper;
import com.nutrilife.fitnessservice.model.dto.MeetingRequestDTO;
import com.nutrilife.fitnessservice.model.dto.MeetingResponseDTO;
import com.nutrilife.fitnessservice.model.dto.ScheduleResponseDTO;
import com.nutrilife.fitnessservice.model.entity.CustomerProfile;
import com.nutrilife.fitnessservice.model.entity.Meeting;
import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.SpecialistProfile;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;
import com.nutrilife.fitnessservice.model.enums.MeetStatus;
import com.nutrilife.fitnessservice.model.enums.ScheduleStatus;
import com.nutrilife.fitnessservice.repository.CustomerProfileRepository;
import com.nutrilife.fitnessservice.repository.MeetingRepository;
import com.nutrilife.fitnessservice.repository.ScheduleRepository;
import com.nutrilife.fitnessservice.repository.SpecialistProfileRepository;
import com.nutrilife.fitnessservice.service.MeetingService;

@ExtendWith(MockitoExtension.class)
class MeetingServiceTest {

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SpecialistProfileRepository specialistProfileRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private MeetingMapper meetingMapper;
    
    @Mock
    private CustomerProfileRepository customerProfileRepository;

    @InjectMocks
    private MeetingService meetingService;

    private CustomerProfile customerProfile;
    private SpecialistProfile specialistProfile;
    private WeeklySchedule weeklySchedule;
    private Schedule schedule;
    private Meeting meeting;
    private MeetingResponseDTO meetingResponseDTO;
    private MeetingRequestDTO meetingRequestDTO;
    private MeetingMapper meetingMapperTest;
    private ScheduleMapper scheduleMapperTest;
    @BeforeEach
    void setUp() {
        
        meetingMapperTest = new MeetingMapper(new ModelMapper());
        scheduleMapperTest = new ScheduleMapper(new ModelMapper());

        customerProfile = new CustomerProfile();
        customerProfile.setCustId(1L);

        specialistProfile = new SpecialistProfile();
        specialistProfile.setSpecId(1L);

        weeklySchedule = new WeeklySchedule();
        weeklySchedule.setSpecialistProfile(specialistProfile);
        weeklySchedule.setStartDate(LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY)));
        weeklySchedule.setEndDate(weeklySchedule.getStartDate().plusDays(6));

        schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setStatus(ScheduleStatus.ACTIVE);
        schedule.setDate(LocalDate.now());
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(11, 0));
        schedule.setWeeklySchedule(weeklySchedule);

    
        meetingRequestDTO = new MeetingRequestDTO();
        meetingRequestDTO.setScheduleId(1L);
        meetingRequestDTO.setStatus(MeetStatus.COMPLETED.toString());
        meetingRequestDTO.setDate(schedule.getDate());
        meetingRequestDTO.setStartTime(schedule.getStartTime());
        meetingRequestDTO.setEndTime(schedule.getEndTime());
        
        meeting = new Meeting();
        meeting.setMeetingId(1L);
        meeting.setSchedule(schedule);
        meeting.setCustomerProfile(customerProfile);
        meeting.setDate(meetingRequestDTO.getDate());
        meeting.setStartTime(meetingRequestDTO.getStartTime());
        meeting.setEndTime(meetingRequestDTO.getEndTime());
        meeting.setStatus(MeetStatus.PENDING);

        meetingResponseDTO = new MeetingResponseDTO();
        meetingResponseDTO.setMeetingId(1L);
        meetingResponseDTO.setScheduleId(1L);
        meetingResponseDTO.setSpecialistId(1L);
        meetingResponseDTO.setCustomerId(1L);
        meetingResponseDTO.setMeetStatus(MeetStatus.valueOf(meetingRequestDTO.getStatus()));
        meetingResponseDTO.setDate(schedule.getDate());
        meetingResponseDTO.setStartTime(schedule.getStartTime());
        meetingResponseDTO.setEndTime(schedule.getEndTime());
        
        weeklySchedule.setScheduleList(Arrays.asList(schedule));
        specialistProfile.setWeeklySchedules(Arrays.asList(weeklySchedule));
        customerProfile.setMeetings(Arrays.asList(meeting));
    }
    @Test
    void testGetAllMeetings() {
        when(meetingRepository.findAll()).thenReturn(Arrays.asList(meeting));
        when(meetingMapper.convertToListDTO(anyList())).thenReturn(Arrays.asList(new MeetingResponseDTO()));
        
        //test meetingMapper 
        List<MeetingResponseDTO> meetingList = meetingMapperTest.convertToListDTO(Arrays.asList(meeting));
        assertNotNull(meetingList);
        
        //test metodo 
        List<MeetingResponseDTO> result = meetingService.getAllMeetings();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testgetMeetingById() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
        when(meetingMapper.convertToDTO(any(Meeting.class))).thenReturn(meetingResponseDTO);
        
        MeetingResponseDTO meetingResponseDTO = meetingMapperTest.convertToDTO(meeting);
        assertNotNull(meetingResponseDTO);

        MeetingResponseDTO result = meetingService.getMeetingById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getMeetingId());
        verify(meetingRepository, times(1)).findById(1L);
    }

    @Test
    void testgetMeetingById_notFound() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.getMeetingById(1L);
        });
    }

    @Test
    void testCreateMeeting() {
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);
        when(meetingMapper.convertToDTO(any(Meeting.class))).thenReturn(meetingResponseDTO);
        when(meetingMapper.convertToEntity(meetingRequestDTO)).thenReturn(meeting);
        when(meetingMapper.createMeetingRequestDTO(schedule)).thenReturn(meetingRequestDTO);

        MeetingResponseDTO meetingResponseDTO = meetingMapperTest.convertToDTO(meeting);
        assertNotNull(meetingResponseDTO);

        MeetingRequestDTO meetingRequestDTO = meetingMapperTest.createMeetingRequestDTO(schedule);
        assertNotNull(meetingRequestDTO);

        Meeting meeting = meetingMapperTest.convertToEntity(meetingRequestDTO);
        assertNotNull(meeting);

    
        MeetingResponseDTO result = meetingService.createMeeting(1L, 1L);
        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        verify(meetingRepository, times(1)).save(any(Meeting.class));
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }


    @Test
    void testCreateMeeting_CustomerNotFound() {
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFound exception = assertThrows(UserNotFound.class, () -> {
            meetingService.createMeeting(1L, 1L);
        });

        assertEquals("Customer not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateMeeting_ScheduleNotFound() {
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.createMeeting(1L, 1L);
        });

        assertEquals("Schedule not found with id: 1", exception.getMessage());
    }

    @Test
    void testCreateMeeting_ScheduleNotActive() {
        schedule.setStatus(ScheduleStatus.DISABLED);
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            meetingService.createMeeting(1L, 1L);
        });

        assertEquals("Cannot create a meeting for a schedule that is not ACTIVE.", exception.getMessage());
    }



    @Test
    void testUpdateMeeting() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
        when(meetingRepository.save(any(Meeting.class))).thenReturn(meeting);
        when(meetingMapper.convertToDTO(any(Meeting.class))).thenReturn(meetingResponseDTO);

        MeetingResponseDTO meetingResponseDTO = meetingMapperTest.convertToDTO(meeting);
        assertNotNull(meetingResponseDTO);


        MeetingResponseDTO result = meetingService.updateMeeting(1L, meetingRequestDTO);
        assertEquals(1L, result.getMeetingId());
        assertEquals(MeetStatus.COMPLETED, result.getMeetStatus());
        verify(meetingRepository, times(1)).save(any(Meeting.class));
    }
    

    @Test
    void updateMeeting_scheduleNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.updateMeeting(1L, meetingRequestDTO);
        });

        verify(scheduleRepository, times(1)).findById(1L);
        verify(meetingRepository, times(0)).findById(anyLong());
        verify(meetingRepository, times(0)).save(any(Meeting.class));
    }

    @Test
    void updateMeeting_meetingNotFound() {
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.updateMeeting(1L, meetingRequestDTO);
        });

        verify(scheduleRepository, times(1)).findById(1L);
        verify(meetingRepository, times(1)).findById(1L);
        verify(meetingRepository, times(0)).save(any(Meeting.class));
    }

    @Test
    void testDeleteMeeting() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));

        meetingService.deleteMeeting(1L);
        verify(meetingRepository, times(1)).delete(any(Meeting.class));
    }
    
    @Test
    void testDeleteMeeting_meetingNotFound() {
        when(meetingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.deleteMeeting(1L);
        });

        verify(meetingRepository, times(1)).findById(1L);
        verify(scheduleRepository, times(0)).save(any(Schedule.class));
        verify(meetingRepository, times(0)).delete(any(Meeting.class));
    }

    @Test
    void testGetDailySchedulesForCurrentWeek() {
        when(specialistProfileRepository.findById(1L)).thenReturn(Optional.of(specialistProfile));
        when(scheduleMapper.convertToListDTO(anyList())).thenReturn(Arrays.asList(new ScheduleResponseDTO()));

        List<ScheduleResponseDTO> scheduleResponseDTOs = scheduleMapperTest.convertToListDTO(Arrays.asList(schedule));
        assertNotNull(scheduleResponseDTOs);

        List<ScheduleResponseDTO> result = meetingService.getDailySchedulesForCurrentWeek(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(specialistProfileRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDailySchedulesForCurrentWeek_SpecialistNotFound() {
        when(specialistProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            meetingService.getDailySchedulesForCurrentWeek(1L);
        });

        verify(specialistProfileRepository, times(1)).findById(1L);
        verify(scheduleMapper, times(0)).convertToListDTO(anyList());
    }

    @Test
    void testGetDailySchedulesForCurrentWeek_WeeklyScheduleNotFound() {
        weeklySchedule.setStartDate(LocalDate.now().minusDays(7));
        weeklySchedule.setEndDate(LocalDate.now().minusDays(1));
        when(specialistProfileRepository.findById(1L)).thenReturn(Optional.of(specialistProfile));

        assertThrows(ResourceNotFoundException.class, () -> {
            meetingService.getDailySchedulesForCurrentWeek(1L);
        });

        verify(specialistProfileRepository, times(1)).findById(1L);
        verify(scheduleMapper, times(0)).convertToListDTO(anyList());
    }

    @Test
    void testGetMeetingByCustomerId() {
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(customerProfile));
        when(meetingMapper.convertToListDTO(anyList())).thenReturn(Arrays.asList(meetingResponseDTO));
        
        List<MeetingResponseDTO> meetingResponseDTOs = meetingMapperTest.convertToListDTO(Arrays.asList(meeting));
        assertNotNull(meetingResponseDTOs);

        List<MeetingResponseDTO> result = meetingService.getMeetingByCustomerId(1L);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(customerProfileRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMeetingByCustomerId_CustomerNotFound() {
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> {
            meetingService.getMeetingByCustomerId(1L);
        });

        verify(customerProfileRepository, times(1)).findById(1L);
        verify(meetingMapper, times(0)).convertToListDTO(anyList());
    }


}
