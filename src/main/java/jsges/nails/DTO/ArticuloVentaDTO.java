package jsges.nails.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ArticuloVentaDTO extends TipoObjetoDTO {

    public Integer id;
    public String denominacion;
    public Integer linea;

}
