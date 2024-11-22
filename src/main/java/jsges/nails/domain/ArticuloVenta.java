package jsges.nails.domain.articulos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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


        public void asEliminado() {
               this.setEstado(1);
        }
}

