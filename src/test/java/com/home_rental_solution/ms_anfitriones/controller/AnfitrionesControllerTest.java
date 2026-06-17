package com.home_rental_solution.ms_anfitriones.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesRequestDTO;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import com.home_rental_solution.ms_anfitriones.service.AnfitrionesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

@WebMvcTest(AnfitrionesController.class)
public class AnfitrionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnfitrionesService anfitrionesService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AnfitrionesResponseDTO anfitrionResponse;
    private AnfitrionesRequestDTO anfitrionRequest;

    @BeforeEach
    void setUp() {
        anfitrionResponse = new AnfitrionesResponseDTO(
                1L,
                "Juan Perez",
                "juan.perez@mail.com",
                "+56912345678",
                false
        );

        anfitrionRequest = new AnfitrionesRequestDTO(
                "Juan Perez",
                "juan.perez@mail.com",
                "+56912345678"
        );
    }

    // TESTS CRUD

    @Test
    public void testGetAnfitriones() throws Exception {
        when(anfitrionesService.mostrarAnfitriones()).thenReturn(List.of(anfitrionResponse));

        mockMvc.perform(get("/api/v1/anfitriones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idAnfitrion").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[0].email").value("juan.perez@mail.com"))
                .andExpect(jsonPath("$[0].verificado").value(false));
    }

    @Test
    public void testGetPorId() throws Exception {
        when(anfitrionesService.mostrarPorId(1L)).thenReturn(anfitrionResponse);

        mockMvc.perform(get("/api/v1/anfitriones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAnfitrion").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan.perez@mail.com"));
    }

    @Test
    public void testPostAnfitrion() throws Exception {
        when(anfitrionesService.save(any(AnfitrionesRequestDTO.class))).thenReturn(anfitrionResponse);

        // Tu controlador responde con HttpStatus.CREATED (201)
        mockMvc.perform(post("/api/v1/anfitriones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anfitrionRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAnfitrion").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    public void testPutAnfitrion() throws Exception {
        when(anfitrionesService.editar(eq(1L), any(AnfitrionesRequestDTO.class))).thenReturn(anfitrionResponse);

        mockMvc.perform(put("/api/v1/anfitriones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anfitrionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAnfitrion").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    public void testDeleteAnfitrion() throws Exception {
        doNothing().when(anfitrionesService).borrar(1L);

        mockMvc.perform(delete("/api/v1/anfitriones/1"))
                .andExpect(status().isNoContent());

        verify(anfitrionesService, times(1)).borrar(1L);
    }

    // TESTS MÉTODOS EXTRAS

    @Test
    public void testValidar() throws Exception {
        when(anfitrionesService.validar(1L)).thenReturn(true);

        mockMvc.perform(get("/api/v1/anfitriones/1/validar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testVerificar() throws Exception {
        AnfitrionesResponseDTO anfitrionVerificado = new AnfitrionesResponseDTO(
                1L,
                "Juan Perez",
                "juan.perez@mail.com",
                "+56912345678",
                true
        );

        when(anfitrionesService.verificar(1L)).thenReturn(anfitrionVerificado);

        mockMvc.perform(put("/api/v1/anfitriones/1/verificar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.verificado").value(true));
    }

    @Test
    public void testGetPropiedades() throws Exception {
        // Simulamos la lista de objetos de retorno que provee ms-propiedades
        List<Object> propiedadesSimuladas = List.of(
                Map.of("id", 101, "titulo", "Departamento Centro")
        );

        when(anfitrionesService.obtenerPropiedades(1L)).thenReturn(propiedadesSimuladas);

        mockMvc.perform(get("/api/v1/anfitriones/1/propiedades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].titulo").value("Departamento Centro"));
    }
}
