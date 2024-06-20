package com.nutrilife.fitnessservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nutrilife.fitnessservice.model.entity.Schedule;
import com.nutrilife.fitnessservice.model.entity.WeeklySchedule;

import jakarta.transaction.Transactional;
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s.weeklySchedule FROM Schedule s WHERE s.scheduleId = :scheduleId")
    WeeklySchedule findWeeklyScheduleByScheduleId(@Param("scheduleId") Long scheduleId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.meeting.id = :meetingId")
    void deleteByMeetingId(Long meetingId);
}
