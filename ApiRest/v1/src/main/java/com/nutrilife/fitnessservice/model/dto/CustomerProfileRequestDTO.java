package com.nutrilife.fitnessservice.model.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
public class CustomerProfileRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(max = 40, message = "El nombre debe tener como máximo 50 caracteres")
    private String name;

    @Size(min=7, max=15, message = "El numero de celular debe tener entre 7 a 15 digitos")
    @Pattern(regexp = "[0-9]+", message = "El numero de celular solo debe contener digitos")
    private String phoneNumber;

    @NotNull(message = "La edad no puede estar vacio")
    @Min (value = 0, message = "La edad debe ser un número positivo")
    @Max (value = 95, message = "La edad no debe superar los 95 años")
    private Integer age;

    @NotNull(message = "El peso no puede estar vacio")
    @DecimalMin (value = "0.0", inclusive = false,message = "El peso debe ser un número positivo")
    @DecimalMax (value = "300.0", inclusive = true,message = "El peso no debe superar los 300 kilos")
    private Float weight;

    @NotNull(message = "La altura no puede estar vacia")
    @DecimalMin (value = "0.0", inclusive = false,message = "La altura debe ser un número positivo")
    @DecimalMax (value = "2.5", inclusive = true,message = "La altura no debe superar los 3 metros")
    private Float height;

    @Size(max = 50, message = "Las alergias deben tener como máximo 50 caracteres")
    private String alergies;

    @Size(max = 50, message = "El tipo de dieta debe tener como máximo 50 caracteres")
    private String dietType;

    @Size(max = 50, message = "Las restricciones dietéticas deben tener como máximo 50 caracteres")
    private String dietRestriction;
}
