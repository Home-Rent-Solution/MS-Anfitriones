package com.home_rental_solution.ms_anfitriones.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnfitrionesRequestDTO {

    //idAnfitrion no se incluye porque MySQL lo genera
    //verificado no se incluye porque se maneja con put

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private  String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email valido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    private  String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres")
    private  String telefono;
}
