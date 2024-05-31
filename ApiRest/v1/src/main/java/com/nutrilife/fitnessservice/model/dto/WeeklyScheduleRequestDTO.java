package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDate;

import com.nutrilife.fitnessservice.model.enums.WeeklyScheduleStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScheduleRequestDTO {
    
    @NotNull(message = "La fecha de inicio no debe estar vacía")
    @Future(message = "La fecha de inicio debe ser una fecha futura")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "El formato de la fecha debe ser 'yyyy-MM-dd'")
    private LocalDate startDate;

    @NotNull(message = "La fecha de fin no debe estar vacía")
    @Future(message = "La fecha de fin debe ser una fecha futura")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "El formato de la fecha debe ser 'yyyy-MM-dd'")
    private LocalDate endDate;

    @NotNull(message = "El estado no debe estar vacío")
    private String status;
}
