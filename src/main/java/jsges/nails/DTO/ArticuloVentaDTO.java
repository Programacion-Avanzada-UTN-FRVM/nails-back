package jsges.nails.DTO;

import jsges.nails.domain.ArticuloVenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticuloVentaDTO extends TipoObjetoDTO {

    public Integer linea;

    public ArticuloVentaDTO(ArticuloVenta articulo) {
        super(articulo.getId(), articulo.getDenominacion());
        this.linea = articulo.getLinea().getId();
    }

}
