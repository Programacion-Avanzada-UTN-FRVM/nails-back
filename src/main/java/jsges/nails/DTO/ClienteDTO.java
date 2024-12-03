package jsges.nails.DTO;
import java.util.Date;

import jsges.nails.domain.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {

    private Integer id;
    private String razonSocial;
    private String letra;
    private String contacto;
    private String celular;
    private String mail;
    private Date fechaInicio;
    private Date fechaNacimiento;

    public ClienteDTO(Cliente model) {
        this.id = model.getId();
        this.razonSocial=model.getRazonSocial();
        this.celular=model.getCelular();
        this.mail=model.getMail();
    }

}
