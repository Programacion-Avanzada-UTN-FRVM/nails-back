package jsges.nails.service.impl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jsges.nails.domain.Cliente;
import jsges.nails.domain.ItemServicio;
import jsges.nails.domain.Servicio;
import jsges.nails.dto.ServicioDTO;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ServicioRepository;
import jsges.nails.service.IClienteService;
import jsges.nails.service.IItemServicioService;
import jsges.nails.service.ITipoServicioService;

class ServicioServiceTest {

    @InjectMocks
    private ServicioService service;

    @Mock
    private ServicioRepository repository;

    @Mock
    private IItemServicioService itemServicioService;

    @Mock
    private IClienteService clienteService;

    @Mock
    private ITipoServicioService tipoServicioService;

    Servicio servicioTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servicioTest = Servicio.builder().cliente(Cliente.builder().mail("arielcometto@gmail.com").build()).build();
    }

    @Test
    void testBuscarPorId_ExistentId() {
        Servicio mockServicio = new Servicio();
        mockServicio.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockServicio));

        Servicio result = service.buscarPorId(1);

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
    void testGetServicios() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Servicio> mockPage = new PageImpl<>(List.of(new Servicio()));
        when(repository.findAll(pageable)).thenReturn(mockPage);

        Page<Servicio> result = service.getServicios(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindPaginated() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ServicioDTO> mockList = List.of(new ServicioDTO());
        Page<ServicioDTO> result = service.findPaginated(pageable, mockList);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testListarPorConsulta() {
        List<Servicio> mockServicios = List.of(servicioTest);
        when(repository.buscarNoEliminados("consulta")).thenReturn(mockServicios);

        List<ServicioDTO> result = service.listar("consulta");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados("consulta");
    }

}
