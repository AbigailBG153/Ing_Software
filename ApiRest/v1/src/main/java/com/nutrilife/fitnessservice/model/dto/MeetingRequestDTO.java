package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRequestDTO {

    @NotNull(message = "Los dias de la semana no pueden estar vacios")
    private LocalDate date;

    @NotNull(message = "La hora final no debe estar nulo ")
    @NotBlank(message = "La hora final no debe estar vacio ")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "El formato de la hora final debe ser 'HH:mm:ss'")
    private LocalTime startTime;

    @NotNull(message = "La hora final no debe estar nulo ")
    @NotBlank(message = "La hora final no debe estar vacio ")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "El formato de la hora final debe ser 'HH:mm:ss'")
    private LocalTime endTime;

    @NotNull(message = "El estatus no debe estar vacio ")
    @Pattern(regexp = "PENDING|COMPLETED|PAID", message = "El estatus debe ser 'PENDING', 'COMPLETED' o 'PAID'")
    private String status;

    private Long ScheduleId;
}