package jsges.nails.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

import jsges.nails.domain.ItemServicio;
import jsges.nails.repository.ItemServicioRepository;

class ItemServicioServiceTest {

    @InjectMocks
    private ItemServicioService service;

    @Mock
    private ItemServicioRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        List<ItemServicio> mockList = List.of(new ItemServicio());
        when(repository.findAll()).thenReturn(mockList);

        List<ItemServicio> result = service.listar();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Null() {
        ItemServicio result = service.buscarPorId(1);

        assertNull(result);
    }

    @Test
    void testGuardar() {
        ItemServicio mockItem = new ItemServicio();
        mockItem.setId(1);
        when(repository.save(mockItem)).thenReturn(mockItem);

        ItemServicio result = service.guardar(mockItem);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).save(mockItem);
    }

    @Test
    void testFindPaginated_Null() {
        Page<ItemServicio> result = service.findPaginated(PageRequest.of(0, 10), new ArrayList<>());

        assertNull(result);
    }

    @Test
    void testGetItemServicios_Null() {
        Page<ItemServicio> result = service.getItemServicios(PageRequest.of(0, 10));

        assertNull(result);
    }

    @Test
    void testBuscarPorServicio() {
        Integer idServicio = 1;
        List<ItemServicio> mockList = List.of(new ItemServicio());
        when(repository.buscarPorServicio(idServicio)).thenReturn(mockList);

        List<ItemServicio> result = service.buscarPorServicio(idServicio);

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarPorServicio(idServicio);
    }
}

