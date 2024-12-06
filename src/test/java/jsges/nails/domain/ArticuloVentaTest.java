package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticuloVentaTest {

    private ArticuloVenta articuloVenta;
    private Linea linea;

    @BeforeEach
    void setUp() {
        linea = new Linea(); // Asume que tienes una clase Linea configurada.
        linea.setId(1);

        articuloVenta = ArticuloVenta.builder()
                .id(1)
                .denominacion("Artículo de prueba")
                .estado(0)
                .observacion("Observación inicial")
                .linea(linea)
                .build();
    }

    @Test
    void testCrearArticuloVenta() {
        assertNotNull(articuloVenta);
        assertEquals(1, articuloVenta.getId());
        assertEquals("Artículo de prueba", articuloVenta.getDenominacion());
        assertEquals(0, articuloVenta.getEstado());
        assertEquals("Observación inicial", articuloVenta.getObservacion());
        assertEquals(linea, articuloVenta.getLinea());
    }

    @Test
    void testActualizarDenominacion() {
        articuloVenta.setDenominacion("Nueva denominación");
        assertEquals("Nueva denominación", articuloVenta.getDenominacion());
    }

    @Test
    void testActualizarEstado() {
        articuloVenta.setEstado(2);
        assertEquals(2, articuloVenta.getEstado());
    }

    @Test
    void testEliminarArticulo() {
        assertFalse(articuloVenta.isEliminado());
        articuloVenta.eliminar();
        assertTrue(articuloVenta.isEliminado());
        assertEquals(1, articuloVenta.getEstado());
    }

    @Test
    void testActualizarObservacion() {
        articuloVenta.setObservacion("Nueva observación");
        assertEquals("Nueva observación", articuloVenta.getObservacion());
    }

    @Test
    void testAsociarLinea() {
        Linea nuevaLinea = new Linea();
        nuevaLinea.setId(2);

        articuloVenta.setLinea(nuevaLinea);

        assertEquals(2, articuloVenta.getLinea().getId());
    }

    @Test
    void testToString() {
        String toString = articuloVenta.toString();
        assertTrue(toString.contains("ArticuloVenta"));
        assertTrue(toString.contains("Artículo de prueba"));
        assertTrue(toString.contains("Observación inicial"));
    }

    @Test
    void testBuilderPattern() {
        ArticuloVenta nuevoArticulo = ArticuloVenta.builder()
                .id(2)
                .denominacion("Otro artículo")
                .estado(0)
                .observacion("Otra observación")
                .linea(linea)
                .build();

        assertNotNull(nuevoArticulo);
        assertEquals(2, nuevoArticulo.getId());
        assertEquals("Otro artículo", nuevoArticulo.getDenominacion());
        assertEquals("Otra observación", nuevoArticulo.getObservacion());
        assertEquals(linea, nuevoArticulo.getLinea());
    }
}
