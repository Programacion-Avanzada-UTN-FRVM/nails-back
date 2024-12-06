package jsges.nails.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jsges.nails.domain.ItemServicio;
import jsges.nails.domain.Servicio;
import jsges.nails.domain.TipoServicio;

import java.sql.Timestamp;

public class ItemServicioDTOTest {
    
    @Test
    void testItemServicioDTOConstructor() {
        Servicio servicio = new Servicio();
        servicio.setId(1);
        servicio.setEstado(0);
        servicio.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        servicio.setFechaRealizacion(new Timestamp(System.currentTimeMillis()));
        servicio.setTotal(150.0);

        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setCodigo(1);
        tipoServicio.setDenominacion("Tipo A");

        ItemServicio itemServicio = new ItemServicio(servicio, tipoServicio, 100.0, "Observación");

        ItemServicioDTO itemServicioDTO = new ItemServicioDTO(itemServicio);

        assertEquals(itemServicio.getId(), itemServicioDTO.getId());
        assertEquals("Tipo A", itemServicioDTO.getTipoServicio());
        assertEquals(1, itemServicioDTO.getTipoServicioId());
        assertEquals(100.0, itemServicioDTO.getPrecio());
        assertEquals("Observación", itemServicioDTO.getObservaciones());
    }

    @Test
    void testBuilderConstructor() {
        ItemServicioDTO itemServicioDTO = ItemServicioDTO.builder()
            .id(1)
            .tipoServicio("Servicio B")
            .tipoServicioId(2)
            .precio(200.0)
            .observaciones("Observación B")
            .build();

        // Verificar los valores asignados por el builder
        assertEquals(1, itemServicioDTO.getId());
        assertEquals("Servicio B", itemServicioDTO.getTipoServicio());
        assertEquals(2, itemServicioDTO.getTipoServicioId());
        assertEquals(200.0, itemServicioDTO.getPrecio());
        assertEquals("Observación B", itemServicioDTO.getObservaciones());
    }

    @Test
    void testEqualsAndHashCode() {
        ItemServicioDTO item1 = new ItemServicioDTO(1, "Tipo A", 1, 100.0, "Observación");
        ItemServicioDTO item2 = new ItemServicioDTO(1, "Tipo A", 1, 100.0, "Observación");

        ItemServicioDTO item3 = new ItemServicioDTO(2, "Tipo B", 2, 200.0, "Otra observación");

        assertTrue(item1.equals(item2));

        assertFalse(item1.equals(item3));

        assertEquals(item1.hashCode(), item2.hashCode());

        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testToString() {
        ItemServicioDTO itemServicioDTO = new ItemServicioDTO(1, "Tipo A", 1, 100.0, "Observación");

        String expectedToString = "ItemServicioDTO(id=1, tipoServicio=Tipo A, tipoServicioId=1, precio=100.0, observaciones=Observación)";
        assertEquals(expectedToString, itemServicioDTO.toString());
    }
    
}