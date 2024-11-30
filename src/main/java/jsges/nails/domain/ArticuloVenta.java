package jsges.nails.domain;

import jakarta.persistence.*; //no se debe importar 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticuloVenta {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(columnDefinition = "TEXT")
        String denominacion;

        //no tiene etiqueta, estado tipo int ?
        int estado;

        @Column(columnDefinition = "TEXT")
        String observacion;

        @ManyToOne(cascade = CascadeType.ALL)
        private Linea linea;


        //nombre del metodo !?
        // ya usa el @Data
        public void asEliminado() {
               this.setEstado(1);
        }
}

