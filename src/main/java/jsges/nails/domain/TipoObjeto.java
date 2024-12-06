package jsges.nails.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public class TipoObjeto implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_objeto_id_seq")
        @SequenceGenerator(name = "tipo_objeto_id_seq", sequenceName = "tipo_objeto_id_seq", allocationSize = 1)
        private Integer id;

        private int codigo;

        @Column(columnDefinition = "TEXT")
        private String denominacion;

        private int estado;

        @Column(columnDefinition = "TEXT")
        private String detalle;

        public void eliminar() {
            this.setEstado(1);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TipoObjeto other = (TipoObjeto) obj;
            return Objects.equals(this.id, other.id);
        }
        
}
