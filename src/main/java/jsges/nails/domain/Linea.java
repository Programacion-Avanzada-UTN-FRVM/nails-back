package jsges.nails.domain.articulos;

import jakarta.persistence.*;
import jsges.nails.DTO.articulos.LineaDTO;
import jsges.nails.domain.TipoObjeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
public class Linea extends TipoObjeto {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Integer id;

    //@Column(columnDefinition = "TEXT")
    //String denominacion;
    //int estado;

    //@Column(columnDefinition = "TEXT")
    //String observacion;

    public Linea() {
        // Constructor por defecto necesario para JPA
    }

    public Linea(String nombre) {

        this.setDenominacion(nombre);
    }

    public Linea(LineaDTO model) {
        this.setDenominacion(model.denominacion);

    }
}

