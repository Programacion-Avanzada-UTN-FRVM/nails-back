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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import jsges.nails.DTO.ArticuloVentaDTO;
import jsges.nails.domain.ArticuloVenta;
import jsges.nails.service.IArticuloVentaService;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class ArticuloVentaControllerTest {

    @InjectMocks
    private ArticuloVentaController controller;

    @Mock
    private IArticuloVentaService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<ArticuloVentaDTO> mockList = List.of(new ArticuloVentaDTO(new ArticuloVenta()));
        when(service.listar()).thenReturn(mockList);

        mockMvc.perform(get("/articulos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetItems() throws Exception {
        Page<ArticuloVentaDTO> mockPage = mock(Page.class);
        when(service.findPaginated(any(PageRequest.class), any())).thenReturn(mockPage);

        mockMvc.perform(get("/articulosPageQuery")
                .param("consulta", "")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar() throws Exception {
        ArticuloVentaDTO dto = new ArticuloVentaDTO();
        ArticuloVenta mockArticulo = new ArticuloVenta();
        when(service.guardar(any(ArticuloVentaDTO.class))).thenReturn(mockArticulo);

        mockMvc.perform(post("/articulos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Articulo 1\", \"precio\": 100}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar() throws Exception {
        ArticuloVenta mockArticulo = new ArticuloVenta();
        when(service.buscarPorId(1)).thenReturn(mockArticulo);

        mockMvc.perform(delete("/articuloEliminar/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId() throws Exception {
        ArticuloVenta mockArticulo = new ArticuloVenta();
        when(service.buscarPorId(1)).thenReturn(mockArticulo);

        mockMvc.perform(get("/articulos/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testActualizar() throws Exception {
        ArticuloVentaDTO dto = new ArticuloVentaDTO();
        ArticuloVenta mockArticulo = new ArticuloVenta();
        when(service.buscarPorId(1)).thenReturn(mockArticulo);
        when(service.guardar(any())).thenReturn(mockArticulo);

        mockMvc.perform(put("/articulos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Articulo actualizado\", \"precio\": 200}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar_NotFound() throws Exception {
        when(service.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(delete("/articuloEliminar/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar_InternalServerError() throws Exception {
        when(service.guardar(any(ArticuloVentaDTO.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/articulos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Articulo 1\", \"precio\": 100}"))
               .andExpect(status().isInternalServerError())
               .andDo(MockMvcResultHandlers.print());
    }
}
