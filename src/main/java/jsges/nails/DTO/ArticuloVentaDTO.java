package jsges.nails.DTO;

import jsges.nails.domain.ArticuloVenta;

public class ArticuloVentaDTO extends TipoObjetoDTO {

    public Integer id;
    public String denominacion;
    public Integer linea;

    public ArticuloVentaDTO(ArticuloVenta model) { //usar loombok
        this.id = model.getId();
        this.denominacion=model.getDenominacion();
        this.linea=model.getLinea().getId();
    }

    public ArticuloVentaDTO( ) { //usar lombok

    }
}
