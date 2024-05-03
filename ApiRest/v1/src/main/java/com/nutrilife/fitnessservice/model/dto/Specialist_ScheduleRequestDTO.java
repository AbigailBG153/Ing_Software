package com.nutrilife.fitnessservice.model.dto;


import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Specialist_ScheduleRequestDTO {

    @NotBlank(message = "La fecha de inicio no puede ser vacio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", message = "El formato de la fecha debe ser yyyy-MM-ddTHH:mm")
    private LocalDateTime startDate;

    @NotBlank(message = "La fecha de fin no puede ser vacio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}\\d{2}", message = "El formato de la fecha debe ser yyyy-MM-ddTHH:mm")
    private LocalDateTime endDate;

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "libre|ocupado", message = "El estado debe ser 'libre' o 'ocupado'")
    private String state;

}
