package jsges.nails.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoObjetoTest {

    private TipoObjeto tipoObjeto;

    @BeforeEach
    void setUp() {
        tipoObjeto = new TipoObjeto();
        tipoObjeto.setId(1);
        tipoObjeto.setCodigo(123);
        tipoObjeto.setDenominacion("Tipo A");
        tipoObjeto.setEstado(0);
        tipoObjeto.setDetalle("Detalles del tipo A");
    }

    @Test
    void testCrearTipoObjeto() {
        assertNotNull(tipoObjeto);
        assertEquals(1, tipoObjeto.getId());
        assertEquals(123, tipoObjeto.getCodigo());
        assertEquals("Tipo A", tipoObjeto.getDenominacion());
        assertEquals(0, tipoObjeto.getEstado());
        assertEquals("Detalles del tipo A", tipoObjeto.getDetalle());
    }

    @Test
    void testEliminar() {
        tipoObjeto.eliminar();
        assertEquals(1, tipoObjeto.getEstado());
    }

    @Test
    void testEquals() {
        TipoObjeto otroTipoObjeto = new TipoObjeto();
        otroTipoObjeto.setId(1);
        otroTipoObjeto.setCodigo(123);
        otroTipoObjeto.setDenominacion("Tipo A");
        otroTipoObjeto.setEstado(0);
        otroTipoObjeto.setDetalle("Detalles del tipo A");

        assertTrue(tipoObjeto.equals(otroTipoObjeto));
    }

    @Test
    void testNotEquals() {
        TipoObjeto otroTipoObjeto = new TipoObjeto();
        otroTipoObjeto.setId(2);  // Diferente id
        otroTipoObjeto.setCodigo(124);
        otroTipoObjeto.setDenominacion("Tipo B");
        otroTipoObjeto.setEstado(0);
        otroTipoObjeto.setDetalle("Detalles del tipo B");

        assertFalse(tipoObjeto.equals(otroTipoObjeto));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(tipoObjeto.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        assertFalse(tipoObjeto.equals("String"));
    }

    @Test
    void testHashCodeWithDifferentId() {
        TipoObjeto otroTipoObjeto = new TipoObjeto();
        otroTipoObjeto.setId(2);  // Diferente id
        otroTipoObjeto.setCodigo(123);
        otroTipoObjeto.setDenominacion("Tipo A");
        otroTipoObjeto.setEstado(0);
        otroTipoObjeto.setDetalle("Detalles del tipo A");

        assertNotEquals(tipoObjeto.hashCode(), otroTipoObjeto.hashCode());
    }

    @Test
    void testToString() {
        String toString = tipoObjeto.toString();
        assertTrue(toString.contains("TipoObjeto"));
        assertTrue(toString.contains("id=" + tipoObjeto.getId()));
        assertTrue(toString.contains("denominacion=" + tipoObjeto.getDenominacion()));
    }

    @Test
    void testConstructorWithFields() {
        TipoObjeto tipoObjetoConCampos = new TipoObjeto();
        tipoObjetoConCampos.setId(1);
        tipoObjetoConCampos.setCodigo(123);
        tipoObjetoConCampos.setDenominacion("Tipo A");
        tipoObjetoConCampos.setEstado(0);
        tipoObjetoConCampos.setDetalle("Detalles del tipo A");

        assertEquals(1, tipoObjetoConCampos.getId());
        assertEquals(123, tipoObjetoConCampos.getCodigo());
        assertEquals("Tipo A", tipoObjetoConCampos.getDenominacion());
        assertEquals(0, tipoObjetoConCampos.getEstado());
        assertEquals("Detalles del tipo A", tipoObjetoConCampos.getDetalle());
    }

}