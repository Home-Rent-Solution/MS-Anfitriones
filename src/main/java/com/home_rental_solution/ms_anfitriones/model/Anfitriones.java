package com.home_rental_solution.ms_anfitriones.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false)
    private boolean verificado = false;
}
