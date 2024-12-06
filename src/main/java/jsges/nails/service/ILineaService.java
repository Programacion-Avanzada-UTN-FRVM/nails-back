package jsges.nails.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jsges.nails.domain.Linea;
import jsges.nails.dto.LineaDTO;

public interface ILineaService {

    public List<LineaDTO> listar();

    public Linea buscarPorId(Integer id);

    public Linea guardar(Linea model);

    public void eliminar(Linea model);

    public List<LineaDTO> listar(String consulta);
    
    public Page<Linea> getLineas(Pageable pageable);

    public Page<LineaDTO> findPaginated(Pageable pageable,List<LineaDTO> lineas);

    public List<Linea> buscar(String consulta);

    public Linea newModel(LineaDTO model);

    public LineaDTO update(LineaDTO modelRecibido, Linea model);
}
