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

import jsges.nails.DTO.TipoServicioDTO;
import jsges.nails.domain.TipoServicio;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.ITipoServicioService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TipoServicioControllerTest {

    @InjectMocks
    private TipoServicioController controller;

    @Mock
    private ITipoServicioService modelService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<TipoServicio> mockList = List.of(new TipoServicio());
        when(modelService.listar()).thenReturn(mockList);

        mockMvc.perform(get("/tiposServicios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId() throws Exception {
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setId(1);
        when(modelService.buscarPorId(1)).thenReturn(mockTipoServicio);

        mockMvc.perform(get("/tiposServicios/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId_NotFound() throws Exception {
        when(modelService.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(get("/tiposServicios/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetItems() throws Exception {
        Page<TipoServicio> mockPage = mock(Page.class);
        when(modelService.listar(anyString())).thenReturn(List.of(new TipoServicio()));
        when(modelService.findPaginated(any(PageRequest.class), any())).thenReturn(mockPage);

        mockMvc.perform(get("/tiposServiciosPageQuery")
                .param("consulta", "")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar() throws Exception {
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setId(1);
        when(modelService.newModel(any(TipoServicioDTO.class))).thenReturn(mockTipoServicio);

        mockMvc.perform(post("/tiposServicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Tipo Servicio Prueba\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar() throws Exception {
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setId(1);
        when(modelService.buscarPorId(1)).thenReturn(mockTipoServicio);

        mockMvc.perform(put("/tipoServicioEliminar/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testEliminar_NotFound() throws Exception {
        when(modelService.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(put("/tipoServicioEliminar/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testActualizar() throws Exception {
        TipoServicio mockTipoServicio = new TipoServicio();
        mockTipoServicio.setId(1);
        when(modelService.buscarPorId(1)).thenReturn(mockTipoServicio);

        mockMvc.perform(put("/tiposServicios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Tipo Servicio Actualizado\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testActualizar_NotFound() throws Exception {
        when(modelService.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(put("/tiposServicios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Tipo Servicio Actualizado\"}"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }
}

