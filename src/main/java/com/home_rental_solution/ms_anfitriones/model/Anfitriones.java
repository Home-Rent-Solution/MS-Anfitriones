package com.home_rental_solution.ms_anfitriones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "anfitriones")
public class Anfitriones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnfitrion;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ingresar un email valido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 20, message = "El telefono no puede superar los 20 caracteres")
    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false)
    private boolean verificado = false;
}
