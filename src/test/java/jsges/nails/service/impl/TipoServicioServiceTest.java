package jsges.nails.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jsges.nails.DTO.TipoServicioDTO;
import jsges.nails.domain.TipoServicio;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.TipoServicioRepository;

class TipoServicioServiceTest {

    @InjectMocks
    private TipoServicioService service;

    @Mock
    private TipoServicioRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        List<TipoServicio> mockTiposServicio = List.of(new TipoServicio());
        when(repository.buscarNoEliminados()).thenReturn(mockTiposServicio);

        List<TipoServicio> result = service.listar();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados();
    }

    @Test
    void testBuscarPorId_ExistentId() {
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockTipoServicio));

        TipoServicio result = service.buscarPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorId_NonExistentId() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RecursoNoEncontradoExcepcion.class, () -> {
            service.buscarPorId(1);
        });

        assertEquals("No se encontraron resultados", exception.getMessage());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGuardar() {
        TipoServicio mockTipoServicio = new TipoServicio();
        when(repository.save(any(TipoServicio.class))).thenReturn(mockTipoServicio);

        TipoServicio result = service.guardar(mockTipoServicio);

        assertNotNull(result);
        verify(repository, times(1)).save(any(TipoServicio.class));
    }

    @Test
    void testNewModel() {
        TipoServicioDTO mockDto = new TipoServicioDTO();
        mockDto.denominacion = "Nuevo Tipo Servicio";
        TipoServicio mockTipoServicio = new TipoServicio();
        when(repository.save(any(TipoServicio.class))).thenReturn(mockTipoServicio);

        TipoServicio result = service.newModel(mockDto);

        assertNotNull(result);
        verify(repository, times(1)).save(any(TipoServicio.class));
    }

    @Test
    void testEliminar() {
        TipoServicio mockTipoServicio = new TipoServicio();
        when(repository.save(mockTipoServicio)).thenReturn(mockTipoServicio);

        service.eliminar(mockTipoServicio);

        verify(repository, times(1)).save(mockTipoServicio);
        assertEquals(1, mockTipoServicio.getEstado());
    }

    @Test
    void testListarPorConsulta() {
        List<TipoServicio> mockTiposServicio = List.of(new TipoServicio());
        when(repository.buscarNoEliminados("consulta")).thenReturn(mockTiposServicio);

        List<TipoServicio> result = service.listar("consulta");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados("consulta");
    }

    @Test
    void testBuscarPorConsulta() {
        List<TipoServicio> mockTiposServicio = List.of(new TipoServicio());
        when(repository.buscarExacto("consulta")).thenReturn(mockTiposServicio);

        List<TipoServicio> result = service.buscar("consulta");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).buscarExacto("consulta");
    }

    @Test
    void testBuscarPorConsulta_NoResults() {
        when(repository.buscarExacto("consulta")).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RecursoNoEncontradoExcepcion.class, () -> {
            service.buscar("consulta");
        });

        assertEquals("No se encontraron resultados", exception.getMessage());
        verify(repository, times(1)).buscarExacto("consulta");
    }

    @Test
    void testUpdate() {
        TipoServicioDTO mockDto = new TipoServicioDTO();
        mockDto.denominacion = "Updated Tipo Servicio";
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setDenominacion("Old Tipo Servicio");

        when(repository.save(mockTipoServicio)).thenReturn(mockTipoServicio);

        TipoServicioDTO result = service.update(mockDto, mockTipoServicio);

        assertNotNull(result);
        assertEquals("Updated Tipo Servicio", result.denominacion);
        verify(repository, times(1)).save(mockTipoServicio);
    }

    @Test
    void testGetTiposServicios() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TipoServicio> mockPage = new PageImpl<>(List.of(new TipoServicio()));
        when(repository.findAll(pageable)).thenReturn(mockPage);

        Page<TipoServicio> result = service.getTiposServicios(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TipoServicio> mockList = List.of(new TipoServicio());
        Page<TipoServicio> result = service.findPaginated(pageable, mockList);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}

