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

import jsges.nails.DTO.ServicioDTO;
import jsges.nails.domain.ItemServicio;
import jsges.nails.domain.Servicio;
import jsges.nails.service.IItemServicioService;
import jsges.nails.service.IServicioService;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ServicioControllerTest {

    @InjectMocks
    private ServicioController controller;

    @Mock
    private IServicioService modelService;

    @Mock
    private IItemServicioService itemServicioService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<ServicioDTO> mockList = List.of(new ServicioDTO(new Servicio(), List.of(new ItemServicio())));
        when(modelService.listar()).thenReturn(mockList);

        mockMvc.perform(get("/servicios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(1))
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId() throws Exception {
        Servicio mockServicio = new Servicio();
        List<ItemServicio> mockItems = List.of(new ItemServicio());
        when(modelService.buscarPorId(1)).thenReturn(mockServicio);
        when(itemServicioService.buscarPorServicio(1)).thenReturn(mockItems);

        mockMvc.perform(get("/servicio/1"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPorId_NotFound() throws Exception {
        when(modelService.buscarPorId(1)).thenThrow(RecursoNoEncontradoExcepcion.class);

        mockMvc.perform(get("/servicio/1"))
               .andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetItems() throws Exception {
        Page<ServicioDTO> mockPage = mock(Page.class);
        when(modelService.findPaginated(any(PageRequest.class), any())).thenReturn(mockPage);

        mockMvc.perform(get("/serviciosPageQuery")
                .param("consulta", "")
                .param("page", "0")
                .param("size", "10"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar() throws Exception {
        ServicioDTO mockServicioDTO = new ServicioDTO();
        mockServicioDTO.setId(1);
        when(modelService.guardar(any(ServicioDTO.class))).thenReturn(mockServicioDTO);

        mockMvc.perform(post("/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Servicio Prueba\", \"descripcion\":\"Descripción de prueba\"}"))
               .andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAgregar_InternalServerError() throws Exception {
        when(modelService.guardar(any(ServicioDTO.class))).thenThrow(new RuntimeException("Error interno"));

        mockMvc.perform(post("/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Servicio Prueba\", \"descripcion\":\"Descripción de prueba\"}"))
               .andExpect(status().isInternalServerError())
               .andDo(MockMvcResultHandlers.print());
    }
}

