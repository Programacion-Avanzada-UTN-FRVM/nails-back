package jsges.nails.dto;

import jsges.nails.domain.ItemServicio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemServicioDTO {
    private Integer id ;
    private String tipoServicio ;
    private Integer tipoServicioId ;
    private Double precio;
    private String observaciones;

    public ItemServicioDTO(ItemServicio itemServicio) {
        this.id = itemServicio.getId();
        this.tipoServicio = itemServicio.getTipoServicio().getDenominacion();
        this.tipoServicioId = itemServicio.getTipoServicio().getCodigo();
        this.precio = itemServicio.getPrecio();
        this.observaciones = itemServicio.getObservacion();
    }

}
