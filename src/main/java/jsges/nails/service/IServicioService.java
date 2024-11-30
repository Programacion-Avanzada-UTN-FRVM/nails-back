package jsges.nails.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jsges.nails.DTO.ServicioDTO;
import jsges.nails.domain.Servicio;

public interface IServicioService {
    public List<ServicioDTO> listar();

    public Servicio buscarPorId(Integer id);

    public ServicioDTO guardar(ServicioDTO model);

    public Page<ServicioDTO> findPaginated(Pageable pageable,List<ServicioDTO> servicios);

    public Page<Servicio> getServicios(Pageable pageable);

    public List<ServicioDTO> listar(String consulta);

}
