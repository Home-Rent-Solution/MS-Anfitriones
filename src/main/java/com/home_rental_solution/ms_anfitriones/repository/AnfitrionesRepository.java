package com.home_rental_solution.ms_anfitriones.repository;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnfitrionesRepository extends JpaRepository<Anfitriones, Integer> {

    //***EXTRAS***
    //buscar por email
    Anfitriones findByEmailIgnoreCase(String email);

    //validar si esta verificado
    boolean existsByIdAnfitrionAndVerificadoTrue(Integer idAnfitrion);
}
