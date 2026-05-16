package com.home_rental_solution.ms_anfitriones.repository;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnfitrionesRepository extends JpaRepository<Anfitriones, Long> {

    //***EXTRAS***
    //buscar por email
    @Query("SELECT a FROM Anfitriones a WHERE LOWER(a.email) = LOWER(:email)")
    Optional<Anfitriones> findByEmailIgnoreCase(@Param("email") String email);

    //verificar si el email ya existe
    boolean existsByEmailIgnoreCase(String email);

    //validar si esta verificado
    boolean existsByIdAnfitrionAndVerificadoTrue(Long idAnfitrion);

    //Listar por estado de verificacion
    @Query("SELECT a FROM Anfitriones a WHERE a.verificado = true ORDER BY a.nombre")
    List<Anfitriones> findByVerificadoTrue();

    @Query("SELECT a FROM Anfitriones a WHERE a.verificado = false ORDER BY a.nombre")
    List<Anfitriones> findByVerificadoFalse();
}
