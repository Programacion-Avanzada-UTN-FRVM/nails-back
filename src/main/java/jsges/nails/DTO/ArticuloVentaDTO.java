package jsges.nails.DTO;

import jsges.nails.domain.ArticuloVenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticuloVentaDTO extends TipoObjetoDTO {

    public Integer id;
    public String denominacion;
    public Integer linea;


    public ArticuloVentaDTO(ArticuloVenta articulo) {
        ArticuloVentaDTO.builder()
        .id(articulo.getId())
        .denominacion(articulo.getDenominacion())
        .linea(articulo.getLinea().getId())
        .build();
    }

}
