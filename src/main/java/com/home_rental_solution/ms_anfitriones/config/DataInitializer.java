package com.home_rental_solution.ms_anfitriones.config;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AnfitrionesRepository anfitrionesRepository;

    @Override
    public void run (String...args){
        //evita insertar datos duplicados en cada arranque
        if (anfitrionesRepository.count() > 0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial");
            return;
        }
        log.info(">>> DataInitializer: BD vacia detectada, insertando anfitriones de prueba");
        anfitrionesRepository.save(new Anfitriones(
                null,
                "Victor Urra",
                "victor.urra@email.com",
                "123456789",
                true
        ));anfitrionesRepository.save(new Anfitriones(
                null,
                "Jenny Salamanca",
                "jenny.salamanca@email.com",
                "987654321",
                true
        ));anfitrionesRepository.save(new Anfitriones(
                null,
                "Eloisa Molina",
                "eloisa.molina@email.com",
                "159357789",
                false
        ));

        log.info(">>> DataInitializer: {} anfitriones insertados correctamente", anfitrionesRepository.count());
    }
}
