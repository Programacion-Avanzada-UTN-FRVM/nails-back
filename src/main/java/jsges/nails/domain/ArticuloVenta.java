package jsges.nails.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ArticuloVenta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(columnDefinition = "TEXT")
        String denominacion;

        int estado;

        @Column(columnDefinition = "TEXT")
        String observacion;

        @ManyToOne(cascade = CascadeType.ALL)
        private Linea linea;

        public void eliminar() {
               this.setEstado(1);
        }

        public boolean isEliminado() {
                return this.estado == 1;
        }
}
