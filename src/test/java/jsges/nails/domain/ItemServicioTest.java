package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ItemServicioTest {
    
    private ItemServicio itemServicio;

    @Mock
    private TipoServicio tipoServicio;

    @Mock
    private Servicio servicio;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        // Crea la instancia de ItemServicio
        itemServicio = ItemServicio.builder()
                .id(1)
                .estado(0)
                .observacion("Observación de prueba")
                .tipoServicio(tipoServicio)
                .servicio(servicio)
                .precio(100.0)
                .build();
    }

    @Test
    void testCrearItemServicio() {
        assertNotNull(itemServicio);
        assertEquals(1, itemServicio.getId());
        assertEquals(0, itemServicio.getEstado());
        assertEquals("Observación de prueba", itemServicio.getObservacion());
        assertEquals(tipoServicio, itemServicio.getTipoServicio());
        assertEquals(servicio, itemServicio.getServicio());
        assertEquals(100.0, itemServicio.getPrecio());
    }

    @Test
    void testActualizarEstado() {
        itemServicio.setEstado(1);
        assertEquals(1, itemServicio.getEstado());
    }

    @Test
    void testEliminar() {
        assertFalse(itemServicio.getEstado() == 1);
        itemServicio.eliminar();
        assertTrue(itemServicio.getEstado() == 1);
    }

    @Test
    void testEqualsAndHashCode() {
        ItemServicio anotherItem = ItemServicio.builder()
                .id(1)
                .estado(0)
                .observacion("Observación de prueba")
                .tipoServicio(tipoServicio)
                .servicio(servicio)
                .precio(100.0)
                .build();

        assertTrue(itemServicio.equals(anotherItem));
        assertEquals(itemServicio.hashCode(), anotherItem.hashCode());
    }

    @Test
    void testNotEquals() {
        ItemServicio differentItem = ItemServicio.builder()
                .id(2)  // Diferente id
                .estado(0)
                .observacion("Otra observación")
                .tipoServicio(tipoServicio)
                .servicio(servicio)
                .precio(200.0)
                .build();

        assertFalse(itemServicio.equals(differentItem));
    }

    @Test
    void testToString() {
        String toString = itemServicio.toString();
        assertTrue(toString.contains("ItemServicio"));
        assertTrue(toString.contains("Observación de prueba"));
        assertTrue(toString.contains("100.0"));
    }

    @Test
    void testConstructorConParametros() {
        Servicio servicioParam = new Servicio();
        TipoServicio tipoServicioParam = new TipoServicio();
        ItemServicio itemServicioParam = new ItemServicio(servicioParam, tipoServicioParam, 200.0, "Nueva observación");

        assertEquals(servicioParam, itemServicioParam.getServicio());
        assertEquals(tipoServicioParam, itemServicioParam.getTipoServicio());
        assertEquals(200.0, itemServicioParam.getPrecio());
        assertEquals("Nueva observación", itemServicioParam.getObservacion());
    }

}
