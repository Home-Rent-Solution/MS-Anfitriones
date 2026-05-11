package com.home_rental_solution.ms_anfitriones.service;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnfitrionesService {

    private final AnfitrionesRepository anfitrionesRepository;

    //***CRUD***
    //GET /anfitriones
    public List<Anfitriones> mostrarAnfitriones(){
        return anfitrionesRepository.findAll();
    }

    //GET /anfitriones/id
    public Anfitriones mostrarPorId(int idAnfitrion){
        return anfitrionesRepository.findById(idAnfitrion).orElse(null);
    }

    //POST /anfitrion
    public Anfitriones save (Anfitriones nuevoAnfitrion) throws Exception{
        if (anfitrionesRepository.existsByEmailIgnoreCase(nuevoAnfitrion.getEmail())){
            throw new Exception("El email ya esta registrado");
        }
        return anfitrionesRepository.save(nuevoAnfitrion);
    }

    //PUT /anfitriones/id
    public Anfitriones editar(int idAnfitrion, Anfitriones anfitrionEditado) throws Exception{
        Anfitriones anfitrionExistente = anfitrionesRepository.findById(idAnfitrion).orElseThrow(() -> new Exception(
                "El Anfitrion con ID: " + idAnfitrion + " no existe"
        ));
        Optional<Anfitriones> anfitrionEmail = anfitrionesRepository.findByEmailIgnoreCase(anfitrionEditado.getEmail());
        if (anfitrionEmail.isPresent() && !anfitrionEmail.get().getIdAnfitrion().equals(idAnfitrion)){
            throw new Exception("El email ya esta registrado por otro anfitrion");
        }
        anfitrionEditado.setIdAnfitrion(idAnfitrion);
        anfitrionEditado.setVerificado(anfitrionExistente.isVerificado());
        return anfitrionesRepository.save(anfitrionEditado);
    }

    //DELETE /anfitriones/id
    public void borrar(int idAnfitrion) throws Exception{
        if (!anfitrionesRepository.existsById(idAnfitrion)){
            throw new Exception("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        anfitrionesRepository.deleteById(idAnfitrion);
    }

    //***EXTRAS***
    //GET /anfitriones/id/validar
    public boolean validar(int idAnfitrion) throws Exception{
        if (!anfitrionesRepository.existsById(idAnfitrion)){
            throw new Exception("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        return anfitrionesRepository.existsByIdAnfitrionAndVerificadoTrue(idAnfitrion);
    }

    //PUT /anfitriones/id/verificar
    public Anfitriones verificar(int idAnfitrion) throws Exception{
        Anfitriones anfitrion = anfitrionesRepository.findById(idAnfitrion).orElseThrow(() -> new Exception("El anfitrion" +
                "con ID: " + idAnfitrion + " no existe"));

        anfitrion.setVerificado(true);
        return anfitrionesRepository.save(anfitrion);
    }
}
