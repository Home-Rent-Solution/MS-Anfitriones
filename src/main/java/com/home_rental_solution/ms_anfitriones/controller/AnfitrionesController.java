package com.home_rental_solution.ms_anfitriones.controller;

import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.service.AnfitrionesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/anfitriones")
@RequiredArgsConstructor
public class AnfitrionesController {

    private final AnfitrionesService anfitrionesService;

    //***CRUD***
    //GET /api/v1/anfitriones
    @GetMapping
    public ResponseEntity<List<Anfitriones>> getAnfitriones(){
        return ResponseEntity.ok(anfitrionesService.mostrarAnfitriones());
    }

    //GET /api/v1/anfitriones/id
    @GetMapping("{idAnfitrion}")
    public ResponseEntity<?> getPorId(@PathVariable int idAnfitrion){
        Anfitriones anfitrion = anfitrionesService.mostrarPorId(idAnfitrion);
        if (anfitrion == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El anfitrion con ID: " + idAnfitrion + " no existe");
        }
        return ResponseEntity.ok(anfitrion);
    }

    // POST /api/v1/anfitriones
    @PostMapping
    public ResponseEntity<?> postAnfitrion(@Valid @RequestBody Anfitriones nuevoAnfitrion){
        try{
            Anfitriones anfitrionGuardado = anfitrionesService.save(nuevoAnfitrion);
            return ResponseEntity.status(HttpStatus.CREATED).body(anfitrionGuardado);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT /api/v1/anfitriones/id
    @PutMapping("{idAnfitrion}")
    public ResponseEntity<?> putAnfitrion(@PathVariable int idAnfitrion, @Valid @RequestBody Anfitriones anfitrionEditado){
        try{
            Anfitriones anfitrionActualizado = anfitrionesService.editar(idAnfitrion, anfitrionEditado);
            return ResponseEntity.ok(anfitrionActualizado);
        } catch (Exception e){
            String msg = e.getMessage();
            if (msg != null && msg.contains("email")){
                return ResponseEntity.badRequest().body(msg);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
    }

    //DELETE /api/v1/anfitriones/id
    @DeleteMapping("{idAnfitrion}")
    public ResponseEntity<?> deleteAnfitrion(@PathVariable int idAnfitrion){
        try{
            anfitrionesService.borrar(idAnfitrion);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //***EXTRAS***
    //GET /api/v1/anfitriones/id/validar
    @GetMapping("{idAnfitrion}/validar")
    public ResponseEntity<?> validar(@PathVariable int idAnfitrion){
        try{
            boolean habilitado = anfitrionesService.validar(idAnfitrion);
            return ResponseEntity.ok(habilitado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //PUT /api/v1/anfitriones/id/verificar
    @PutMapping("{idAnfitrion}/verificar")
    public ResponseEntity<?> verificar(@PathVariable int idAnfitrion){
        try{
            Anfitriones anfitrion = anfitrionesService.verificar(idAnfitrion);
            return ResponseEntity.ok(anfitrion);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //GET /api/v1/anfitriones/id/propiedades
    @GetMapping("{idAnfitrion}/propiedades")
    public ResponseEntity<?> getPropiedades(@PathVariable int idAnfitrion){
        return ResponseEntity.ok("Endpoint pendiente de integracion con ms-propiedades");
    }

    //Manejo de errores de validacion
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> manejarErroresValidacion(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return errores;
    }
}
