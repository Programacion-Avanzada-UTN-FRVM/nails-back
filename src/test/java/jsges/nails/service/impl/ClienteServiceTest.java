package jsges.nails.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

import jsges.nails.DTO.ClienteDTO;
import jsges.nails.domain.Cliente;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ClienteRepository;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListar() {
        List<Cliente> mockList = List.of(new Cliente());
        when(repository.buscarNoEliminados()).thenReturn(mockList);

        List<ClienteDTO> result = service.listar();

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados();
    }

    @Test
    void testBuscarPorId_Encontrado() {
        Cliente mockCliente = new Cliente();
        mockCliente.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(mockCliente));

        Cliente result = service.buscarPorId(1);

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
        Cliente mockCliente = new Cliente();
        mockCliente.setId(1);
        mockCliente.setRazonSocial("Cliente Test");

        when(repository.save(mockCliente)).thenReturn(mockCliente);

        Cliente result = service.guardar(mockCliente);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Cliente Test", result.getRazonSocial());
        verify(repository, times(1)).save(mockCliente);
    }

    @Test
    void testEliminar() {
        Cliente mockCliente = new Cliente();
        mockCliente.setId(1);

        service.eliminar(mockCliente);

        assertEquals(1, mockCliente.getEstado());
        verify(repository, times(1)).save(mockCliente);
    }

    @Test
    void testListarPorConsulta() {
        List<Cliente> mockList = List.of(new Cliente());
        when(repository.buscarNoEliminados("consulta")).thenReturn(mockList);

        List<Cliente> result = service.listar("consulta");

        assertEquals(1, result.size());
        verify(repository, times(1)).buscarNoEliminados("consulta");
    }

    @Test
    void testGetClientes() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Cliente> mockPage = new PageImpl<>(List.of(new Cliente()));

        when(repository.findAll(pageable)).thenReturn(mockPage);

        Page<Cliente> result = service.getClientes(pageable);

        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void testFindPaginated() {
        List<ClienteDTO> mockList = List.of(new ClienteDTO());
        PageRequest pageable = PageRequest.of(0, 10);

        Page<ClienteDTO> result = service.findPaginated(pageable, mockList);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void testUpdate() {
        ClienteDTO dto = new ClienteDTO();
        dto.setCelular("123456789");
        dto.setContacto("Nuevo Contacto");
        dto.setRazonSocial("Nueva Razón Social");
        dto.setLetra("A");
        dto.setMail("test@mail.com");

        Cliente mockCliente = new Cliente();

        ClienteDTO result = service.update(dto, mockCliente);

        assertNotNull(result);
        assertEquals("Nueva Razón Social", result.getRazonSocial());
        assertEquals("test@mail.com", result.getMail());
    }
    
}

