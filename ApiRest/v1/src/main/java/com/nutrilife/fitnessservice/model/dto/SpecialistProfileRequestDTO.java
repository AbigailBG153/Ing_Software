package com.nutrilife.fitnessservice.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialistProfileRequestDTO {
    
    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 50, message = "El nombre debe tener como máximo 50 caracteres")
    private String name;

    @Size(min=7, max=15, message = "El numero de celular debe tener entre 7 a 15 digitos")
    @Pattern(regexp = "[0-9]+", message = "El numero de celular solo debe contener digitos")
    private String phoneNumber;

    @NotNull(message = "La edad no puede estar vacio")
    @Min (value = 0, message = "La edad no debe ser un número positivo")
    private Integer age;

    @Min(value = 0, message = "La puntuacion debe ser un numero positvo")
    @Max(value = 5, message = "La puntuacion debe ser como maximo 5")
    private Integer score;

    @NotBlank(message = "La ocupacion no puede estar vacia")
    @Size(max=15, message = "La ocupacion debe tener como maximo 15 caracteres")
    private String ocupation;

    private MultipartFile cv;

    private MultipartFile studCertificate;

    private String email;
    
    private String password;

}
