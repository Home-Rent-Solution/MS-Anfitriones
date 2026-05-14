package com.home_rental_solution.ms_anfitriones.controller;

import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesRequestDTO;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import com.home_rental_solution.ms_anfitriones.service.AnfitrionesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anfitriones")
@RequiredArgsConstructor
public class AnfitrionesController {

    private final AnfitrionesService anfitrionesService;

    //***CRUD***
    //GET /api/v1/anfitriones
    @GetMapping
    public ResponseEntity<List<AnfitrionesResponseDTO>> getAnfitriones(){
        return ResponseEntity.ok(anfitrionesService.mostrarAnfitriones());
    }

    //GET /api/v1/anfitriones/id
    @GetMapping("{idAnfitrion}")
    public ResponseEntity<AnfitrionesResponseDTO> getPorId(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.mostrarPorId(idAnfitrion));
    }

    // POST /api/v1/anfitriones
    @PostMapping
    public ResponseEntity<AnfitrionesResponseDTO> postAnfitrion(@Valid @RequestBody AnfitrionesRequestDTO dto){
            return ResponseEntity.status(HttpStatus.CREATED).body(anfitrionesService.save(dto));
        }

    //PUT /api/v1/anfitriones/id
    @PutMapping("{idAnfitrion}")
    public ResponseEntity<AnfitrionesResponseDTO> putAnfitrion(@PathVariable int idAnfitrion, @Valid @RequestBody
    AnfitrionesRequestDTO dto){
            return ResponseEntity.ok(anfitrionesService.editar(idAnfitrion, dto));
        }

    //DELETE /api/v1/anfitriones/id
    @DeleteMapping("{idAnfitrion}")
    public ResponseEntity<Void> deleteAnfitrion(@PathVariable int idAnfitrion){
        anfitrionesService.borrar(idAnfitrion);
            return ResponseEntity.noContent().build();
    }

    //***EXTRAS***
    //GET /api/v1/anfitriones/id/validar
    @GetMapping("{idAnfitrion}/validar")
    public ResponseEntity<Boolean> validar(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.validar(idAnfitrion));
    }

    //PUT /api/v1/anfitriones/id/verificar
    @PutMapping("{idAnfitrion}/verificar")
    public ResponseEntity<AnfitrionesResponseDTO> verificar(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.verificar(idAnfitrion));
    }

    //GET /api/v1/anfitriones/id/propiedades
    @GetMapping("{idAnfitrion}/propiedades")
    public ResponseEntity<List<Object>> getPropiedades(@PathVariable int idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.obtenerPropiedades(idAnfitrion));
    }
}