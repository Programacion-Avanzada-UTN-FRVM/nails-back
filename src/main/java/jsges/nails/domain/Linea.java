package jsges.nails.domain;

import jakarta.persistence.*;
import jsges.nails.DTO.LineaDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
@NoArgsConstructor
public class Linea extends TipoObjeto {

    @Column(columnDefinition = "TEXT")
    String observacion;

    public Linea(String nombre) {
        this.setDenominacion(nombre);
    }

    public Linea(LineaDTO model) {
        this.setDenominacion(model.denominacion);
    }

}
