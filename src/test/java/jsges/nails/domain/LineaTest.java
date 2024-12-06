package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jsges.nails.dto.LineaDTO;

public class LineaTest {
    
    private Linea linea;

    @BeforeEach
    void setUp() {
        linea = Linea.builder()
                .observacion("Observación de la línea 1")
                .build();
    }

    @Test
    void testCrearLinea() {
        assertNotNull(linea);
        linea.setDenominacion("Observación de la línea 1");
        assertEquals("Observación de la línea 1", linea.getDenominacion());
    }

    @Test
    void testActualizarObservacion() {
        linea.setObservacion("Nueva observación");
        assertEquals("Nueva observación", linea.getObservacion());
    }

    @Test
    void testConstructorConNombre() {
        Linea lineaConNombre = new Linea("Linea 2");
        assertEquals("Linea 2", lineaConNombre.getDenominacion());
    }

    @Test
    void testConstructorConDTO() {
        LineaDTO lineaDTO = new LineaDTO(Linea.builder().observacion("test").build());
        Linea lineaDesdeDTO = new Linea(lineaDTO);
        assertEquals("test", lineaDesdeDTO.getDenominacion());
    }

    @Test
    void testToString() {
        String toString = linea.toString();
        assertTrue(toString.contains("Linea"));
        assertFalse(toString.contains("Observación de la línea 1"));
    }

    @Test
    void testEqualsAndHashCode() {
        Linea linea2 = Linea.builder()
                .observacion("Observación de la línea 1")
                .build();

        assertTrue(linea.equals(linea2));
        assertEquals(linea.hashCode(), linea2.hashCode());
    }


}
