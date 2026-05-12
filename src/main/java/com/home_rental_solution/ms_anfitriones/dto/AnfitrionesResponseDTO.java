package com.home_rental_solution.ms_anfitriones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnfitrionesResponseDTO {

    private Integer isAnfitrion;
    private String nombre;
    private String email;
    private String telefono;
    private boolean verificado;
}
