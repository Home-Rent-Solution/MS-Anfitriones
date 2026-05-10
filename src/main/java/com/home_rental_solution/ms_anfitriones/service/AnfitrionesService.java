package com.home_rental_solution.ms_anfitriones.service;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnfitrionesService {

    @Autowired
    private AnfitrionesRepository anfitrionesRepository;
    //***CRUD***
    //GET /anfitriones
    public List<Anfitriones> mostrarAnfitriones(){
        return anfitrionesRepository.obtenerAnfitriones();
    }

    //GET /anfitriones/id
    public Anfitriones mostrarPorId(int idAnfitrion){
        return anfitrionesRepository.buscarPorId(idAnfitrion);
    }

    //POST /anfitrion
    public Anfitriones save (Anfitriones nuevoAnfitrion) throws Exception{
        if (anfitrionesRepository.buscarPorEmail(nuevoAnfitrion.getEmail()) != null){
            throw new Exception("El email ya esta registrado");
        }
        return anfitrionesRepository.guardar(nuevoAnfitrion);
    }

    //PUT /anfitriones/id
    public Anfitriones editar(int idAnfitrion, Anfitriones anfitrionEditado) throws Exception{
        Anfitriones anfitrionExistente = anfitrionesRepository.buscarPorId(idAnfitrion);
        if (anfitrionExistente == null){
            throw new Exception("El anfitrion con Id " + idAnfitrion + " no existe");
        }
        Anfitriones anfitrionEmail = anfitrionesRepository.buscarPorEmail(anfitrionEditado.getEmail());
        if (anfitrionEmail != null && !anfitrionEmail.getIdAnfitrion().equals(idAnfitrion)){
            throw new Exception("El email ya esta registrado por otro anfitrion");
        }
        anfitrionEditado.setIdAnfitrion(idAnfitrion);
        return anfitrionesRepository.actualizar(anfitrionEditado);
    }

    //DELETE /anfitriones/id
    public void borrar(int idAnfitrion) throws Exception{
        if (anfitrionesRepository.buscarPorId(idAnfitrion) == null){
            throw new Exception("El anfitrion con ID " + idAnfitrion + " no existe");
        }
        anfitrionesRepository.eliminar(idAnfitrion);
    }

    //***EXTRAS***
    //GET /anfitriones/id/validar
    public boolean validar(int idAnfitrion) throws Exception{
        Anfitriones anfitrion = anfitrionesRepository.buscarPorId(idAnfitrion);
        if (anfitrion == null){
            throw new Exception("El anfitrion con ID " + idAnfitrion + " no existe");
        }
        return anfitrionesRepository.estaVerificado(idAnfitrion);
    }

    //PUT /anfitriones/id/verificar
    public Anfitriones verificar(int idAnfitrion) throws Exception{
        Anfitriones anfitrion = anfitrionesRepository.buscarPorId(idAnfitrion);
        if (anfitrion == null){
            throw new Exception("El anfitrion con ID " + idAnfitrion + " no existe");
        }
        return anfitrionesRepository.verificar(idAnfitrion);
    }
}
