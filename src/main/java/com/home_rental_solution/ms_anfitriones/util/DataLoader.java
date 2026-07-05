package com.home_rental_solution.ms_anfitriones.util;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Profile({"dev", "test"})
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final AnfitrionesRepository anfitrionesRepository;


    @Autowired
    public DataLoader(AnfitrionesRepository anfitrionesRepository) {
        this.anfitrionesRepository = anfitrionesRepository;
    }

    @Override
    public void run (String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        //generamos 15 anfitriones
        for (int i = 0; i < 15; i++) {
            Anfitriones anfitriones = new Anfitriones();
            anfitriones.setNombre(faker.name().fullName());
            anfitriones.setEmail(faker.internet().emailAddress());
            anfitriones.setTelefono(faker.phoneNumber().cellPhone());
            anfitriones.setVerificado(faker.bool().bool());
            anfitrionesRepository.save(anfitriones);
        }
        log.info(">>ms-anfitriones: ¡Base de datos poblada con anfitriones (y estados de verificación) exitosamente!");
    }
}
