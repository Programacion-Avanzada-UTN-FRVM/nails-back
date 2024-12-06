package jsges.nails.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jsges.nails.domain.ArticuloVenta;
import jsges.nails.dto.ArticuloVentaDTO;

public interface IArticuloVentaService {

    public List<ArticuloVentaDTO> listar();

    public ArticuloVenta buscarPorId(Integer id);

    public ArticuloVenta guardar(ArticuloVentaDTO model);

    public void eliminar(ArticuloVenta model);

    public List<ArticuloVentaDTO> listar(String consulta);

    public Page<ArticuloVenta> getArticulos(Pageable pageable);

    public Page<ArticuloVentaDTO> findPaginated(Pageable pageable, List<ArticuloVentaDTO> list);

    public ArticuloVentaDTO update(ArticuloVentaDTO modelRecibido, ArticuloVenta model);
}
