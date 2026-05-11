package com.home_rental_solution.ms_anfitriones.repository;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnfitrionesRepository extends JpaRepository<Anfitriones, Integer> {

    //***EXTRAS***
    //buscar por email
    Optional<Anfitriones> findByEmailIgnoreCase(String email);

    //verificar si el email ya existe
    boolean existsByEmailIgnoreCase(String email);

    //validar si esta verificado
    boolean existsByIdAnfitrionAndVerificadoTrue(Integer idAnfitrion);

    //Listar por estado de verificacion
    List<Anfitriones> findByVerificadoTrue();
    List<Anfitriones> findByVerificadoFalse();
}
