package com.home_rental_solution.ms_anfitriones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "ms-propiedades"
)
public interface PropiedadClient {

    //GET /propiedades/anfitrion/id
    //obtener propiedades de un anfitrion
    @GetMapping("/api/v1/propiedades/anfitrion/{idAnfitrion}")
    List<Object> obtenerPropiedadesPorAnfitrion(@PathVariable Long idAnfitrion);
}
