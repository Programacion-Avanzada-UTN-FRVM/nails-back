package jsges.nails.service.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import jsges.nails.domain.ArticuloVenta;
import jsges.nails.domain.Linea;
import jsges.nails.dto.ArticuloVentaDTO;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ArticuloVentaRepository;
import jsges.nails.service.ILineaService;

class ArticuloVentaServiceTest {

    @InjectMocks
    private ArticuloVentaService service;

    @Mock
    private ArticuloVentaRepository repository;

    @Mock
    private ILineaService lineaService;

    ArticuloVenta articuloTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        articuloTest = ArticuloVenta.builder().linea(Linea.builder().observacion("test").build()).build();
    }

    @Test
    void testListar() {
        List<ArticuloVenta> mockList = List.of(articuloTest);
        when(repository.buscarNoEliminados()).thenReturn(mockList);

        List<ArticuloVentaDTO> result = service.listar();

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados();
    }

    @Test
    void testBuscarPorId_Encontrado() {
        ArticuloVenta mockArticulo = new ArticuloVenta();
        mockArticulo.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockArticulo));

        ArticuloVenta result = service.buscarPorId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoExcepcion.class, () -> service.buscarPorId(1));
        verify(repository, times(1)).findById(1);
    }

    @Test
    void testGuardar() {
        ArticuloVentaDTO dto = new ArticuloVentaDTO();
        dto.setDenominacion("Articulo Test");
        dto.setLinea(1);

        Linea mockLinea = new Linea();
        mockLinea.setId(1);

        ArticuloVenta mockArticulo = new ArticuloVenta();
        mockArticulo.setId(1);
        mockArticulo.setDenominacion("Articulo Test");

        when(lineaService.buscarPorId(1)).thenReturn(mockLinea);
        when(repository.save(any(ArticuloVenta.class))).thenReturn(mockArticulo);

        ArticuloVenta result = service.guardar(dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Articulo Test", result.getDenominacion());
        verify(lineaService, times(1)).buscarPorId(1);
        verify(repository, times(1)).save(any(ArticuloVenta.class));
    }

    @Test
    void testEliminar() {
        ArticuloVenta mockArticulo = new ArticuloVenta();
        mockArticulo.setId(1);

        service.eliminar(mockArticulo);

        verify(repository, times(1)).save(mockArticulo);
        assertTrue(mockArticulo.isEliminado());
    }

    @Test
    void testListarPorConsulta() {
        List<ArticuloVenta> mockList = List.of(articuloTest);
        when(repository.buscarNoEliminados("test")).thenReturn(mockList);

        List<ArticuloVentaDTO> result = service.listar("test");

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados("test");
    }

    @Test
    void testGetArticulos() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<ArticuloVenta> mockPage = new PageImpl<>(List.of(new ArticuloVenta()));

        when(repository.findAll(pageable)).thenReturn(mockPage);

        Page<ArticuloVenta> result = service.getArticulos(pageable);

        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindPaginated() {
        List<ArticuloVentaDTO> mockList = List.of(new ArticuloVentaDTO());
        PageRequest pageable = PageRequest.of(0, 10);

        Page<ArticuloVentaDTO> result = service.findPaginated(pageable, mockList);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void testUpdate() {
        ArticuloVentaDTO dto = new ArticuloVentaDTO();
        dto.setDenominacion("Articulo Actualizado");
        dto.setLinea(1);

        Linea mockLinea = new Linea();
        mockLinea.setId(1);

        ArticuloVenta mockArticulo = new ArticuloVenta();
        mockArticulo.setId(1);

        when(lineaService.buscarPorId(1)).thenReturn(mockLinea);

        ArticuloVentaDTO result = service.update(dto, mockArticulo);

        assertNotNull(result);
        assertEquals("Articulo Actualizado", mockArticulo.getDenominacion());
        assertEquals(1, mockArticulo.getLinea().getId());
        verify(lineaService, times(1)).buscarPorId(1);
    }
}

