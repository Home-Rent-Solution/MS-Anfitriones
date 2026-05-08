package com.home_rental_solution.ms_anfitriones.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anfitriones {

    @NotNull(message = "El ID del anfitrion no debe estar vacio")
    private Integer idAnfitrion;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email valido")
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    private String telefono;

    private boolean verificado;
}
