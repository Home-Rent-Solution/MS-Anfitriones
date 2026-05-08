package com.home_rental_solution.ms_anfitriones.repository;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class AnfitrionesRepository {

    private List<Anfitriones> listaAnfitriones = new ArrayList<>();
    private int contadorId = 4;

    public AnfitrionesRepository(){
        listaAnfitriones.add(new Anfitriones(
                1,
                "Victor Urra",
                "victor.urra@email.com",
                "123456789",
                true
        ));listaAnfitriones.add(new Anfitriones(
                2,
                "Jenny Salamanca",
                "jenny.salamanca@email.com",
                "987654321",
                true
        ));listaAnfitriones.add(new Anfitriones(
                3,
                "Eloisa Molina",
                "eloisa.molina@email.com",
                "159951357",
                false
        ));
    }

    //***CRUD***
    //obtener todos los anfitriones
    public List<Anfitriones> obtenerAnfitriones(){
        return listaAnfitriones;
    }

    //Buscar por ID
    public Anfitriones buscarPorId(int idAnfitrion){
        for (Anfitriones anfitrion : listaAnfitriones){
            if (anfitrion.getIdAnfitrion() == idAnfitrion){
                return anfitrion;
            }
        }
        return null;
    }

    //guardar nuevo anfitrion
    public Anfitriones guardar(Anfitriones anfitrion){
        anfitrion.setIdAnfitrion(contadorId++);
        listaAnfitriones.add(anfitrion);
        return anfitrion;
    }

    //actualizar anfitrion
    public Anfitriones actualizar(Anfitriones anfitrion){
        for (int i = 0; i < listaAnfitriones.size(); i++){
            if (listaAnfitriones.get(i).getIdAnfitrion().equals(anfitrion.getIdAnfitrion())){
                listaAnfitriones.set(i, anfitrion);
                return anfitrion;
            }
        }
        return null;
    }

    //eliminar por ID
    public void eliminar(int idAnfitrion){
        Anfitriones anfitrion = buscarPorId(idAnfitrion);
        if (anfitrion != null){
            listaAnfitriones.remove(anfitrion);
        }
    }

    //***EXTRAS***
    //buscar por email
    public Anfitriones buscarPorEmail(String email){
        for (Anfitriones anfitrion : listaAnfitriones){
            if (anfitrion.getEmail().equalsIgnoreCase(email)){
                return anfitrion;
            }
        }
        return null;
    }

    //verificar anfitrion
    public Anfitriones verificar(int idAnfitrion){
        Anfitriones anfitrion = buscarPorId(idAnfitrion);
        if (anfitrion != null){
            anfitrion.setVerificado(true);
        }
        return anfitrion;
    }

    //validar si esta habilitado
    public boolean estaVerificado(int idAnfitrion){
        Anfitriones anfitrion = buscarPorId(idAnfitrion);
        return anfitrion != null && anfitrion.isVerificado();
    }
}
