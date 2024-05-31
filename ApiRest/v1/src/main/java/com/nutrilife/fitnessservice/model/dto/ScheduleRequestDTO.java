package com.nutrilife.fitnessservice.model.dto;

//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ScheduleRequestDTO {

    @NotNull(message = "Los dias de la semana no pueden estar vacios")
    @Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)$", message = "El día de la semana debe estar en el formato correcto (Lunes, Martes, Miércoles, etc.)")
    private String dayOfWeek;

    @NotNull(message = "El día de la semana debe estar vacio ")
    @Future
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}", message = "El formato de la fecha debe ser 'yyyy-MM-dd'")
    private LocalDate date;

    @NotNull(message = "La hora de inicio no debe estar vacio ")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "El formato de la hora inicio debe ser 'HH:mm:ss'")
    private LocalTime startTime;

    @NotNull(message = "La hora final no debe estar nulo ")
    @NotBlank(message = "La hora final no debe estar vacio ")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "El formato de la hora final debe ser 'HH:mm:ss'")
    private LocalTime endTime;

    @NotNull(message = "El estatus no debe estar vacio ")
    @Pattern(regexp = "DISABLED|ACTIVE|OCCUPIED", message = "El estatus debe ser 'DISABLED', 'ACTIVE' o 'OCCUPIED'")
    private String status;

}