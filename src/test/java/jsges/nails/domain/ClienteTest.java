package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClienteTest {
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = Cliente.builder()
                .id(1)
                .razonSocial("Cliente de Prueba")
                .estado(0)
                .letra("A")
                .contacto("Juan Pérez")
                .celular("123456789")
                .mail("juan.perez@example.com")
                .fechaInicio(Date.from(Instant.now().minusSeconds(3600)))
                .fechaNacimiento(Date.from(Instant.now()))
                .build();
    }

    @Test
    void testCrearCliente() {
        assertNotNull(cliente);
        assertEquals(1, cliente.getId());
        assertEquals("Cliente de Prueba", cliente.getRazonSocial());
        assertEquals(0, cliente.getEstado());
        assertEquals("A", cliente.getLetra());
        assertEquals("Juan Pérez", cliente.getContacto());
        assertEquals("123456789", cliente.getCelular());
        assertEquals("juan.perez@example.com", cliente.getMail());
        assertNotNull(cliente.getFechaInicio());
        assertNotNull(cliente.getFechaNacimiento());
    }

    @Test
    void testActualizarEstado() {
        cliente.setEstado(1);
        assertEquals(1, cliente.getEstado());
    }

    @Test
    void testEqualsAndHashCode() {
        Cliente anotherCliente = Cliente.builder()
                .id(1)
                .razonSocial("Cliente de Prueba")
                .estado(0)
                .letra("A")
                .contacto("Juan Pérez")
                .celular("123456789")
                .mail("juan.perez@example.com")
                .fechaInicio(Date.from(Instant.now().minusSeconds(3600)))
                .fechaNacimiento(Date.from(Instant.now()))
                .build();

        assertTrue(cliente.equals(anotherCliente));
        assertEquals(cliente.hashCode(), anotherCliente.hashCode());
    }

    @Test
    void testNotEquals() {
        Cliente differentCliente = Cliente.builder()
                .id(2)  // Diferente id
                .razonSocial("Otro Cliente")
                .estado(1)
                .letra("B")
                .contacto("Pedro Gómez")
                .celular("987654321")
                .mail("pedro.gomez@example.com")
                .fechaInicio(Date.from(Instant.now().minusSeconds(3600)))
                .fechaNacimiento(Date.from(Instant.now()))
                .build();

        assertFalse(cliente.equals(differentCliente));
    }

    @Test
    void testToString() {
        String toString = cliente.toString();
        assertTrue(toString.contains("Cliente"));
        assertTrue(toString.contains("Cliente de Prueba"));
        assertTrue(toString.contains("juan.perez@example.com"));
    }
    
    @Test
    void testConstructorConParametros() {
        Cliente clienteParam = new Cliente(1, "Cliente Param", 0, "B", "Carlos Martínez", "987654321", "carlos.martinez@example.com",
        Date.from(Instant.now().minusSeconds(3600)), Date.from(Instant.now()));

        assertEquals(1, clienteParam.getId());
        assertEquals("Cliente Param", clienteParam.getRazonSocial());
        assertEquals(0, clienteParam.getEstado());
        assertEquals("B", clienteParam.getLetra());
        assertEquals("Carlos Martínez", clienteParam.getContacto());
        assertEquals("987654321", clienteParam.getCelular());
        assertEquals("carlos.martinez@example.com", clienteParam.getMail());
        assertNotNull(clienteParam.getFechaInicio());
        assertNotNull(clienteParam.getFechaNacimiento());
    }

}
