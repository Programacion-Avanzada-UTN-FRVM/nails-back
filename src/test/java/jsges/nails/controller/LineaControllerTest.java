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

import jsges.nails.DTO.LineaDTO;
import jsges.nails.domain.Linea;
import jsges.nails.service.ILineaService;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LineaControllerTest {

    @InjectMocks
    private LineaController controller;

    @Mock
    private ILineaService modelService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<LineaDTO> mockList = List.of(new LineaDTO(new Linea()));
        when(modelService.listar()).thenReturn(mockList);

        mockMvc.perform(get("/lineas"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetItems() throws Exception {
        Page<LineaDTO> mockPage = mock(Page.class);
        when(modelService.findPaginated(any(PageRequest.class), any())).thenReturn(mockPage);

        mockMvc.perform(get("/lineasPageQuery")
                .param("consulta", "")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar() throws Exception {
        Linea mockLinea = new Linea();
        when(modelService.newModel(any(LineaDTO.class))).thenReturn(mockLinea);

        mockMvc.perform(post("/linea")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Linea 1\", \"descripcion\":\"Descripcion de la linea\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar() throws Exception {
        Linea mockLinea = new Linea();
        when(modelService.buscarPorId(1)).thenReturn(mockLinea);

        mockMvc.perform(put("/lineaEliminar/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId() throws Exception {
        Linea mockLinea = new Linea();
        when(modelService.buscarPorId(1)).thenReturn(mockLinea);

        mockMvc.perform(get("/linea/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testActualizar() throws Exception {
        LineaDTO dto = new LineaDTO();
        Linea mockLinea = new Linea();
        when(modelService.buscarPorId(1)).thenReturn(mockLinea);
        when(modelService.guardar(any(Linea.class))).thenReturn(mockLinea);

        mockMvc.perform(put("/linea/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Linea actualizada\", \"descripcion\":\"Descripcion actualizada\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar_NotFound() throws Exception {
        when(modelService.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(put("/lineaEliminar/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar_InternalServerError() throws Exception {
        when(modelService.newModel(any(LineaDTO.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/linea")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Linea 1\", \"descripcion\":\"Descripcion de la linea\"}"))
               .andExpect(status().isInternalServerError())
               .andDo(MockMvcResultHandlers.print());
    }
}

