package jsges.nails.domain;

import jakarta.persistence.*;
import jsges.nails.DTO.LineaDTO;
import lombok.Data;
import lombok.ToString;


@Entity
@Data
@ToString
public class Linea extends TipoObjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    String denominacion;

    //no tiene etiqueta, estado tipo int ??
    int estado;

    @Column(columnDefinition = "TEXT")
    String observacion;

    public Linea() {
        // Constructor por defecto necesario para JPA

        //se podria usar el que te genera el @NoArgsConstructor de Loombock ?
    }

    public Linea(String nombre) {

        this.setDenominacion(nombre);
    }

    public Linea(LineaDTO model) {
        this.setDenominacion(model.denominacion);

    }
}

