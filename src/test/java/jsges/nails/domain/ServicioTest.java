package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ServicioTest {
    
    private Servicio servicio;
    private Cliente clienteMock;

    @BeforeEach
    void setUp() {

        clienteMock = Mockito.mock(Cliente.class);

        servicio = Servicio.builder()
                .id(1)
                .estado(0)
                .cliente(clienteMock)
                .fechaRegistro(new Timestamp(new Date().getTime()))
                .fechaRealizacion(new Timestamp(new Date().getTime()))
                .total(150.75)
                .build();
    }

    @Test
    void testCrearServicio() {
        assertNotNull(servicio);
        assertEquals(1, servicio.getId());
        assertEquals(0, servicio.getEstado());
        assertEquals(clienteMock, servicio.getCliente());
        assertNotNull(servicio.getFechaRegistro());
        assertNotNull(servicio.getFechaRealizacion());
        assertEquals(150.75, servicio.getTotal());
    }

    @Test
    void testActualizarEstado() {
        servicio.setEstado(1);
        assertEquals(1, servicio.getEstado());
    }

    @Test
    void testActualizarTotal() {
        servicio.setTotal(200.50);
        assertEquals(200.50, servicio.getTotal());
    }

    @Test
    void testToString() {
        String toString = servicio.toString();
        assertTrue(toString.contains("Servicio"));
        assertTrue(toString.contains("id=" + servicio.getId()));
        assertTrue(toString.contains("total=" + servicio.getTotal()));
    }

    @Test
    void testNotEquals() {
        Servicio servicioDiferente = Servicio.builder()
                .id(2)
                .estado(1)
                .cliente(clienteMock)
                .fechaRegistro(new Timestamp(new Date().getTime()))
                .fechaRealizacion(new Timestamp(new Date().getTime()))
                .total(200.00)
                .build();

        assertFalse(servicio.equals(servicioDiferente));
    }

    @Test
    void testClienteRelacion() {
        Cliente nuevoCliente = new Cliente();
        servicio.setCliente(nuevoCliente);

        assertEquals(nuevoCliente, servicio.getCliente());
    }
    
}
