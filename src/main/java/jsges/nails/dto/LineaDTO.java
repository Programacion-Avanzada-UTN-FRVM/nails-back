package jsges.nails.dto;

import jsges.nails.domain.Linea;

public class LineaDTO extends TipoObjetoDTO {

    public LineaDTO() {
       super(); 
    }

    public LineaDTO(Linea linea) {
        this.id= linea.getId();
        this.denominacion= linea.getDenominacion();
    }
}
