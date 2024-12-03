package jsges.nails.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

import jsges.nails.DTO.LineaDTO;
import jsges.nails.domain.Linea;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.LineaRepository;

class LineaServiceTest {

    @InjectMocks
    private LineaService service;

    @Mock
    private LineaRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        List<Linea> mockList = List.of(new Linea());
        when(repository.buscarNoEliminados()).thenReturn(mockList);

        List<LineaDTO> result = service.listar();

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados();
    }

    @Test
    void testBuscarPorId_ExistentId() {
        Linea mockLinea = new Linea();
        mockLinea.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockLinea));

        Linea result = service.buscarPorId(1);

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

        assertEquals("No se encontro el id: 1", exception.getMessage());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGuardar() {
        Linea mockLinea = new Linea();
        when(repository.save(mockLinea)).thenReturn(mockLinea);

        Linea result = service.guardar(mockLinea);

        assertNotNull(result);
        verify(repository, times(1)).save(mockLinea);
    }

    @Test
    void testNewModel() {
        LineaDTO mockDto = new LineaDTO();
        Linea mockLinea = new Linea(mockDto);
        when(repository.save(any(Linea.class))).thenReturn(mockLinea);

        Linea result = service.newModel(mockDto);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Linea.class));
    }

    @Test
    void testEliminar() {
        Linea mockLinea = new Linea();
        doNothing().when(repository).save(mockLinea);

        service.eliminar(mockLinea);

        verify(repository, times(1)).save(mockLinea);
    }

    @Test
    void testListarPorConsulta() {
        List<Linea> mockList = List.of(new Linea());
        when(repository.buscarNoEliminados("test")).thenReturn(mockList);

        List<LineaDTO> result = service.listar("test");

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados("test");
    }

    @Test
    void testGetLineas() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Linea> mockPage = new PageImpl<>(List.of(new Linea()));
        when(repository.findAll(pageable)).thenReturn(mockPage);

        Page<Linea> result = service.getLineas(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testBuscar() {
        List<Linea> mockList = List.of();
        when(repository.buscarExacto("test")).thenReturn(mockList);

        Exception exception = assertThrows(RecursoNoEncontradoExcepcion.class, () -> {
            service.buscar("test");
        });

        assertEquals("Error con la consulta", exception.getMessage());
        verify(repository, times(1)).buscarExacto("test");
    }

    @Test
    void testFindPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        List<LineaDTO> mockList = List.of(new LineaDTO());
        Page<LineaDTO> result = service.findPaginated(pageable, mockList);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testUpdate() {
        Linea mockLinea = new Linea();
        LineaDTO mockDto = new LineaDTO();
        mockDto.setDenominacion("New Denomination");

        LineaDTO result = service.update(mockDto, mockLinea);

        assertNotNull(result);
        assertEquals("New Denomination", result.getDenominacion());
    }
}

