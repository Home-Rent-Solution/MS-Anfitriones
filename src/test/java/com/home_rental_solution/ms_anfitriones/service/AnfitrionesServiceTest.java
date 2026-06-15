package com.home_rental_solution.ms_anfitriones.service;

import com.home_rental_solution.ms_anfitriones.client.PropiedadClient;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesRequestDTO;
import com.home_rental_solution.ms_anfitriones.dto.AnfitrionesResponseDTO;
import com.home_rental_solution.ms_anfitriones.model.Anfitriones;
import com.home_rental_solution.ms_anfitriones.repository.AnfitrionesRepository;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class AnfitrionesServiceTest {

    @Autowired
    private AnfitrionesService anfitrionesService;

    @MockitoBean
    private AnfitrionesRepository anfitrionesRepository;

    @MockitoBean
    private PropiedadClient propiedadClient;

    //Test CRUD

    @Test
    public void testMostrarAnfitriones() {
        Anfitriones anfitrion = new Anfitriones(
                1L,
                "Juan Perez",
                "juan@mail.com",
                "123456789",
                false
        );
        when(anfitrionesRepository.findAll()).thenReturn(List.of(anfitrion));
        List<AnfitrionesResponseDTO> resultado = anfitrionesService.mostrarAnfitriones();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
    }

    @Test
    public void testMostrarPorId_Success() {
        Anfitriones anfitrion = new Anfitriones(
                1L,
                "Pedro Gomez",
                "pedro@mail.com",
                "987654321",
                true
        );
        when(anfitrionesRepository.findById(1L)).thenReturn(Optional.of(anfitrion));
        AnfitrionesResponseDTO resultado = anfitrionesService.mostrarPorId(1L);
        assertNotNull(resultado);
        assertEquals("Pedro Gomez", resultado.getNombre());
    }

    @Test
    public void testMostrarPorId_NotFound() {
        when(anfitrionesRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            anfitrionesService.mostrarPorId(99L);
        });
        assertEquals("El anfitrion con ID: 99 no existe", exception.getMessage());
    }

    @Test
    public void testSave_Success() {
        AnfitrionesRequestDTO request = new AnfitrionesRequestDTO(
                "Diego Torres",
                "diego@mail.com",
                "5555"
        );
        Anfitriones anfitrionGuardado = new Anfitriones(
                1L,
                request.getNombre(),
                request.getEmail(),
                request.getTelefono(),
                false
        );
        when(anfitrionesRepository.existsByEmailIgnoreCase("diego@mail.com")).thenReturn(false);
        when(anfitrionesRepository.save(any(Anfitriones.class))).thenReturn(anfitrionGuardado);
        AnfitrionesResponseDTO resultado = anfitrionesService.save(request);
        assertNotNull(resultado);
        assertEquals(
                1L,
                resultado.getIdAnfitrion()
        );
        assertFalse(resultado.isVerificado());
    }

    @Test
    public void testSave_EmailYaExiste() {
        AnfitrionesRequestDTO request = new AnfitrionesRequestDTO(
                "Diego Torres",
                "diego@mail.com",
                "5555"
        );

        when(anfitrionesRepository.existsByEmailIgnoreCase("diego@mail.com")).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            anfitrionesService.save(request);
        });
        assertEquals(
                "El email ya esta registrado",
                exception.getMessage()
        );
    }

    @Test
    public void testEditar_Success() {
        Anfitriones anfitrionExistente = new Anfitriones(
                1L,
                "Antiguo",
                "antiguo@mail.com",
                "111",
                false
        );
        AnfitrionesRequestDTO nuevosDatos = new AnfitrionesRequestDTO(
                "Nuevo Nombre",
                "nuevo@mail.com",
                "222"
        );

        when(anfitrionesRepository.findById(1L)).thenReturn(Optional.of(anfitrionExistente));
        when(anfitrionesRepository.findByEmailIgnoreCase("nuevo@mail.com")).thenReturn(Optional.empty());
        when(anfitrionesRepository.save(any(Anfitriones.class))).
                thenAnswer(invocation -> invocation.getArgument(0));
        AnfitrionesResponseDTO resultado = anfitrionesService.editar(
                1L,
                nuevosDatos
        );

        assertEquals(
                "Nuevo Nombre",
                resultado.getNombre()
        );
        assertEquals(
                "nuevo@mail.com",
                resultado.getEmail()
        );
    }

    @Test
    public void testEditar_EmailOcupadoPorOtro() {
        Anfitriones anfitrionExistente = new Anfitriones(
                1L,
                "Juan",
                "juan@mail.com",
                "111",
                false
        );
        Anfitriones otroAnfitrion = new Anfitriones(
                2L,
                "Maria",
                "maria@mail.com",
                "222",
                false
        );
        AnfitrionesRequestDTO nuevosDatos = new AnfitrionesRequestDTO(
                "Juan",
                "maria@mail.com",
                "111"
        );
        when(anfitrionesRepository.findById(1L)).thenReturn(Optional.of(anfitrionExistente));
        when(anfitrionesRepository.findByEmailIgnoreCase("maria@mail.com")).thenReturn(Optional.of(otroAnfitrion));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            anfitrionesService.editar(1L, nuevosDatos);
        });
        assertEquals("El email ya esta registrado por otro anfitrion", exception.getMessage());
    }

    @Test
    public void testBorrar_Success() {
        when(anfitrionesRepository.existsById(1L)).thenReturn(true);
        anfitrionesService.borrar(1L);
        verify(anfitrionesRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testBorrar_NotFound() {
        when(anfitrionesRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> {
            anfitrionesService.borrar(99L);
        });
    }

    //Test de los extras

    @Test
    public void testValidar() {
        when(anfitrionesRepository.existsById(1L)).thenReturn(true);
        when(anfitrionesRepository.existsByIdAnfitrionAndVerificadoTrue(1L)).thenReturn(true);
        boolean resultado = anfitrionesService.validar(1L);
        assertTrue(resultado);
    }

    @Test
    public void testVerificar() {
        Anfitriones anfitrion = new Anfitriones(
                1L,
                "Juan",
                "j@mail.com",
                "123",
                false
        );
        when(anfitrionesRepository.findById(1L)).thenReturn(Optional.of(anfitrion));
        when(anfitrionesRepository.save(any(Anfitriones.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AnfitrionesResponseDTO resultado = anfitrionesService.verificar(1L);
        assertTrue(resultado.isVerificado());
    }

    @Test
    public void testObtenerPropiedades_Success() {
        List<Object> mockPropiedades = List.of(Map.of(
                "idPropiedad",
                101,
                "titulo",
                "Depto")
        );
        when(anfitrionesRepository.existsById(1L)).thenReturn(true);
        when(propiedadClient.obtenerPropiedadesPorAnfitrion(1L)).thenReturn(mockPropiedades);
        List<Object> resultado = anfitrionesService.obtenerPropiedades(1L);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testObtenerPropiedades_Feign404() {
        when(anfitrionesRepository.existsById(1L)).thenReturn(true);
        Request request = Request.create(
                Request.HttpMethod.GET,
                "/propiedades",
                Map.of(),
                null,
                null,
                null
        );
        FeignException.NotFound feignException = new FeignException.NotFound(
                "Not Found",
                request,
                null,
                null
        );
        when(propiedadClient.obtenerPropiedadesPorAnfitrion(1L)).thenThrow(feignException);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            anfitrionesService.obtenerPropiedades(1L);
        });
        assertTrue(exception.getMessage().contains("No se encontraron propiedades para el anfitrion"));
    }
}
