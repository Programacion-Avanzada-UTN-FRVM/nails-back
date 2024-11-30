package jsges.nails.domain;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Cliente implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(columnDefinition = "TEXT")
        String razonSocial;

        int estado;

        @Column(columnDefinition = "TEXT")
        String letra;

        @Column(columnDefinition = "TEXT")
        String contacto;

        @Column(columnDefinition = "TEXT")
        String celular;

        @Column(columnDefinition = "TEXT")
        String mail;

        Date fechaInicio;
        
        Date fechaNacimiento;


}
