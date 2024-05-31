package com.nutrilife.fitnessservice.model.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MeetingRequestDTO{

    @NotBlank(message = "El nombre de la reunion no debe estar vacio")
    private LocalDateTime date;

    @NotBlank(message = "El tipo de plataforma no puede estar vacío")
    private String typePlatform;

    @NotBlank(message = "La fecha de inicio no puede ser vacio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", message = "El formato de la fecha debe ser yyyy-MM-ddTHH:mm")
    private LocalDateTime startDate;

    @NotBlank(message = "La fecha de fin no puede ser vacio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}\\d{2}", message = "El formato de la fecha debe ser yyyy-MM-ddTHH:mm")
    private LocalDateTime endDate;
    
    @NotBlank(message = "La fecha de registro no puede ser vacio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", message = "El formato de la fecha debe ser yyyy-MM-ddTHH:mm")
    private LocalDateTime registerDate;

}