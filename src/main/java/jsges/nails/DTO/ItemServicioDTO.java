package jsges.nails.DTO;

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
        ItemServicioDTO.builder()
        .id(itemServicio.getId())
        .tipoServicio(itemServicio.getTipoServicio().getDenominacion())
        .tipoServicioId(itemServicio.getTipoServicio().getCodigo())
        .precio(itemServicio.getPrecio())
        .observaciones(itemServicio.getObservacion())
        .build();
    }

}
