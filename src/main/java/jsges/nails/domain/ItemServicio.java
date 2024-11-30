package jsges.nails.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@ToString
public class ItemServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // no tiene etiqueta
    int estado;

    @Column(columnDefinition = "TEXT")
    String observacion;

    @ManyToOne(cascade = CascadeType.ALL) //no deberia existir esta relacion, el servicio deberia tener el tipo
    private TipoServicio tipoServicio;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Servicio servicio;

    //no tiene etiqueta
    private Double precio;

    // nombre del metodo !?
    // ya usa el @Data
    public void asEliminado() {
        this.setEstado(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemServicio that = (ItemServicio) o;
        return estado == that.estado && Objects.equals(id, that.id) && Objects.equals(observacion, that.observacion) && Objects.equals(tipoServicio, that.tipoServicio) && Objects.equals(servicio, that.servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estado, observacion, tipoServicio, servicio);
    }

    //constructor deberia ir mas arriba, esta redundante porque ya usa el @AllArgsConstructor
    public ItemServicio() {

    }

    //idem arriba
    public ItemServicio(Servicio servicio ,TipoServicio tipo, Double precio,String observacion) {
        this.servicio = servicio;
        this.tipoServicio = tipo;
        this.precio = precio;
        this.observacion=observacion;
    }
}
