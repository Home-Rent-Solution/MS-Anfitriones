package com.home_rental_solution.ms_anfitriones.controller;

import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesRequestDTO;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import com.home_rental_solution.ms_anfitriones.service.AnfitrionesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/anfitriones")
@RequiredArgsConstructor
@Tag(name = "Anfitriones", description = "Controlador principal para la gestión, registro y verificaciones de" +
        " seguridad de los anfitriones del sistema")
public class AnfitrionesController {

    private final AnfitrionesService anfitrionesService;

    //***CRUD***
    //GET /api/v1/anfitriones
    @GetMapping
    @Operation(summary = "Obtener todos los anfitriones", description = "Devuelve una lista ordenada alfabéticamente" +
            " con todos los anfitriones registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de anfitriones recuperada con éxito",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AnfitrionesResponseDTO.class))))
    public ResponseEntity<List<AnfitrionesResponseDTO>> getAnfitriones(){
        return ResponseEntity.ok(anfitrionesService.mostrarAnfitriones());
    }

    //GET /api/v1/anfitriones/id
    @GetMapping("{idAnfitrion}")
    @Operation(summary = "Obtener un anfitrión por ID", description = "Busca y devuelve el perfil completo de un" +
            " anfitrión basándose en su ID único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anfitrión localizado correctamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = AnfitrionesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "El ID del anfitrión solicitado no existe",
                    content = @Content)
    })
    public ResponseEntity<AnfitrionesResponseDTO> getPorId(@Parameter(description = "ID numérico del anfitrión a buscar"
            , example = "1", required = true)@PathVariable Long idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.mostrarPorId(idAnfitrion));
    }

    // POST /api/v1/anfitriones
    @PostMapping
    @Operation(summary = "Registrar un nuevo anfitrión", description = "Crea un anfitrión en la base de datos de" +
            " manera limpia, validando que el email sea único y tenga el formato correcto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anfitrión registrado de forma exitosa",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = AnfitrionesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación en el JSON de entrada o el correo" +
                    " ya se encuentra registrado", content = @Content)
    })
    public ResponseEntity<AnfitrionesResponseDTO> postAnfitrion(@io.swagger.v3.oas.annotations.parameters.
            RequestBody(description = "Datos requeridos para el alta del nuevo anfitrión",
            required = true)@Valid @RequestBody AnfitrionesRequestDTO dto){
            return ResponseEntity.status(HttpStatus.CREATED).body(anfitrionesService.save(dto));
        }

    //PUT /api/v1/anfitriones/id
    @PutMapping("{idAnfitrion}")
    @Operation(summary = "Actualizar datos de un anfitrión", description = "Permite modificar el nombre o correo de" +
            " un anfitrión mediante su ID. El estado de verificación permanece seguro sin alteraciones directas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = AnfitrionesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos, ID inexistente o" +
                    " conflicto de email duplicado", content = @Content)
    })
    public ResponseEntity<AnfitrionesResponseDTO> putAnfitrion(
            @Parameter(description = "ID del anfitrión que se va a editar", example = "1", required = true)
            @PathVariable Long idAnfitrion,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Esquema con la información" +
                    " actualizada del anfitrión", required = true)
            @Valid @RequestBody AnfitrionesRequestDTO dto){
        return ResponseEntity.ok(anfitrionesService.editar(idAnfitrion, dto));
    }
    //DELETE /api/v1/anfitriones/id
    @DeleteMapping("{idAnfitrion}")
    @Operation(summary = "Eliminar un anfitrión", description = "Remueve permanentemente el registro de un anfitrión" +
            " del sistema utilizando su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anfitrión eliminado con éxito (Sin contenido de" +
                    " respuesta)", content = @Content),
            @ApiResponse(responseCode = "400", description = "No se encontró ningún anfitrión con el ID especificado",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteAnfitrion(
            @Parameter(description = "ID del anfitrión a dar de baja", example = "1", required = true)
            @PathVariable Long idAnfitrion){
        anfitrionesService.borrar(idAnfitrion);
        return ResponseEntity.noContent().build();
    }

    //***EXTRAS***
    //GET /api/v1/anfitriones/id/validar
    @GetMapping("{idAnfitrion}/validar")
    @Operation(summary = "Validar estado de verificación (Uso Interno / OpenFeign)", description = "Endpoint" +
            " estratégico consumido por ms-propiedades. Devuelve true únicamente si el anfitrión existe y tiene su" +
            " flag verificado en true.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación completada. Retorna un booleano con el estado",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "El ID enviado no corresponde a ningún anfitrión",
                    content = @Content)
    })
    public ResponseEntity<Boolean> validar(
            @Parameter(description = "ID del anfitrión a evaluar", example = "1", required = true)
            @PathVariable Long idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.validar(idAnfitrion));
    }

    //PUT /api/v1/anfitriones/id/verificar
    @PutMapping("{idAnfitrion}/verificar")
    @Operation(summary = "Verificar anfitrión", description = "Cambia de manera permanente el estado de seguridad" +
            " de un anfitrión a verificado (verificado = true), habilitándolo para publicar propiedades.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anfitrión verificado satisfactoriamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(
                            implementation = AnfitrionesResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "No se logró verificar debido a que el ID no existe",
                    content = @Content)
    })
    public ResponseEntity<AnfitrionesResponseDTO> verificar(
            @Parameter(description = "ID del anfitrión a autorizar", example = "1", required = true)
            @PathVariable Long idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.verificar(idAnfitrion));
    }

    //GET /api/v1/anfitriones/id/propiedades
    @GetMapping("{idAnfitrion}/propiedades")
    @Operation(summary = "Listar propiedades pertenecientes al anfitrión", description = "Utiliza lógica del servicio" +
            " para mapear o consultar los alojamientos de este anfitrión.")
    @ApiResponse(responseCode = "200", description = "Colección de propiedades asociadas recuperada correctamente",
            content = @Content(mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(implementation = Object.class))))
    public ResponseEntity<List<Object>> getPropiedades(
            @Parameter(description = "ID del anfitrión dueño de los alojamientos", example = "1", required = true)
            @PathVariable Long idAnfitrion){
        return ResponseEntity.ok(anfitrionesService.obtenerPropiedades(idAnfitrion));
    }
}