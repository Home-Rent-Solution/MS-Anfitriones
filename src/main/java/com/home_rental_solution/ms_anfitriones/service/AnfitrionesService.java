package com.home_rental_solution.ms_anfitriones.service;

import com.home_rental_solution.ms_anfitriones.client.PropiedadClient;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesRequestDTO;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnfitrionesService {

    @Autowired
    private final AnfitrionesRepository anfitrionesRepository;

    @Autowired
    private final PropiedadClient propiedadClient;

    //Mapeo: Entidad -> ResponseDTO
    private AnfitrionesResponseDTO mapToDTO(Anfitriones anfitrion){
        return new AnfitrionesResponseDTO(
                anfitrion.getIdAnfitrion(),
                anfitrion.getNombre(),
                anfitrion.getEmail(),
                anfitrion.getTelefono(),
                anfitrion.isVerificado()
        );
    }

    //Mapeo: RequestDTO -> Entidad
    private Anfitriones mapToEntity(AnfitrionesRequestDTO dto){
        return new Anfitriones(
                null,
                dto.getNombre(),
                dto.getEmail(),
                dto.getTelefono(),
                false
        );
    }

    //validacion con feign
    private List<Object> obtenerPropiedadesAnfitrion(Long idAnfitrion){
        try{
            List<Object> propiedades = propiedadClient.obtenerPropiedadesPorAnfitrion(idAnfitrion);
            log.info(
                    ">>> Propiedades del anfitrion {} obtenidas correctamente (Feign Client)",
                    idAnfitrion
            );
            return propiedades;
        } catch (FeignException.NotFound e){
            throw new RuntimeException("No se encontraron propiedades para el anfitrion con ID: " + idAnfitrion);
        } catch (FeignException e){
            throw new RuntimeException("No se puede conectar con ms-propiedades: " + e.getMessage());
        }
    }

    //***CRUD***
    //GET /anfitriones
    public List<AnfitrionesResponseDTO> mostrarAnfitriones(){
        return anfitrionesRepository
                .findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //GET /anfitriones/id
    public AnfitrionesResponseDTO mostrarPorId(Long idAnfitrion){
        Anfitriones anfitrion = anfitrionesRepository
                .findById(idAnfitrion)
                .orElseThrow(() -> new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe"));
        return mapToDTO(anfitrion);
    }

    //POST /anfitrion
    public AnfitrionesResponseDTO save (AnfitrionesRequestDTO dto){
        if (anfitrionesRepository.existsByEmailIgnoreCase(dto.getEmail())){
            throw new RuntimeException("El email ya esta registrado");
        }
        return mapToDTO(anfitrionesRepository.save(mapToEntity(dto)));
    }

    //PUT /anfitriones/id
    public AnfitrionesResponseDTO editar(Long idAnfitrion, AnfitrionesRequestDTO dto){
        Anfitriones anfitrionExistente = anfitrionesRepository
                .findById(idAnfitrion)
                .orElseThrow(() -> new RuntimeException("El Anfitrion con ID: " + idAnfitrion + " no existe"));
        Optional<Anfitriones> anfitrionEmail = anfitrionesRepository.findByEmailIgnoreCase(dto.getEmail());
        if (anfitrionEmail.isPresent() && !anfitrionEmail
                .get()
                .getIdAnfitrion()
                .equals(idAnfitrion)){
            throw new RuntimeException("El email ya esta registrado por otro anfitrion");
        }
        anfitrionExistente.setNombre(dto.getNombre());
        anfitrionExistente.setEmail(dto.getEmail());
        anfitrionExistente.setTelefono(dto.getTelefono());
        return mapToDTO(anfitrionesRepository.save(anfitrionExistente));
    }

    //DELETE /anfitriones/id
    public void borrar(Long idAnfitrion){
        if (!anfitrionesRepository.existsById(idAnfitrion)){
            throw new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        anfitrionesRepository.deleteById(idAnfitrion);
    }

    //***EXTRAS***
    //GET /anfitriones/id/validar
    public boolean validar(Long idAnfitrion){
        if (!anfitrionesRepository.existsById(idAnfitrion)){
            throw new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        return anfitrionesRepository.existsByIdAnfitrionAndVerificadoTrue(idAnfitrion);
    }

    //PUT /anfitriones/id/verificar
    public AnfitrionesResponseDTO verificar(Long idAnfitrion){
        Anfitriones anfitrion = anfitrionesRepository
                .findById(idAnfitrion)
                .orElseThrow(() -> new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe"));
        anfitrion.setVerificado(!anfitrion.isVerificado());
        return mapToDTO(anfitrionesRepository.save(anfitrion));
    }

    //GET /anfitriones/id/propiedades
    public List<Object> obtenerPropiedades(Long idAnfitrion){
        if (!anfitrionesRepository.existsById(idAnfitrion)){
            throw new RuntimeException("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        return obtenerPropiedadesAnfitrion(idAnfitrion);
    }
}
