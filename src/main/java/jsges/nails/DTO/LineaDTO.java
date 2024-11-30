package jsges.nails.DTO;

import jsges.nails.domain.Linea;

public class LineaDTO extends TipoObjetoDTO { //hace falta este dto ?

    public LineaDTO() {
       super(); 
    }

    public LineaDTO(Linea linea) {
        this.id= linea.getId();
        this.denominacion= linea.getDenominacion();
    }
}
