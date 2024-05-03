package com.nutrilife.fitnessservice.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MeetingReportDTO {
    private LocalDateTime date;
    private long meetingCount;
}
