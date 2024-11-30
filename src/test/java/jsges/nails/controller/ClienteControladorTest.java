package jsges.nails.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import jsges.nails.DTO.ClienteDTO;
import jsges.nails.domain.Cliente;
import jsges.nails.service.IClienteService;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClienteControladorTest {

    @InjectMocks
    private ClienteControlador controlador;

    @Mock
    private IClienteService clienteServicio;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controlador).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<ClienteDTO> mockList = List.of(new ClienteDTO(new Cliente()));
        when(clienteServicio.listar()).thenReturn(mockList);

        mockMvc.perform(get("/clientes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetItems() throws Exception {
        Page<ClienteDTO> mockPage = mock(Page.class);
        when(clienteServicio.findPaginated(any(PageRequest.class), any())).thenReturn(mockPage);

        mockMvc.perform(get("/clientesPageQuery")
                .param("consulta", "")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar() throws Exception {
        Cliente mockCliente = new Cliente();
        when(clienteServicio.guardar(any(Cliente.class))).thenReturn(mockCliente);

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Cliente 1\", \"email\": \"cliente1@example.com\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar() throws Exception {
        Cliente mockCliente = new Cliente();
        when(clienteServicio.buscarPorId(1)).thenReturn(mockCliente);

        mockMvc.perform(put("/clienteEliminar/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId() throws Exception {
        Cliente mockCliente = new Cliente();
        when(clienteServicio.buscarPorId(1)).thenReturn(mockCliente);

        mockMvc.perform(get("/cliente/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testActualizar() throws Exception {
        ClienteDTO dto = new ClienteDTO();
        Cliente mockCliente = new Cliente();
        when(clienteServicio.buscarPorId(1)).thenReturn(mockCliente);
        when(clienteServicio.guardar(any(Cliente.class))).thenReturn(mockCliente);

        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Cliente actualizado\", \"email\": \"actualizado@example.com\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar_NotFound() throws Exception {
        when(clienteServicio.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(put("/clienteEliminar/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar_InternalServerError() throws Exception {
        when(clienteServicio.guardar(any(Cliente.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Cliente 1\", \"email\": \"cliente1@example.com\"}"))
               .andExpect(status().isInternalServerError())
               .andDo(MockMvcResultHandlers.print());
    }
}

