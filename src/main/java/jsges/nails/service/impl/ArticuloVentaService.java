package jsges.nails.service.impl;

import jsges.nails.DTO.ArticuloVentaDTO;
import jsges.nails.domain.ArticuloVenta;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ArticuloVentaRepository;
import jsges.nails.service.IArticuloVentaService;
import jsges.nails.service.ILineaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ArticuloVentaService implements IArticuloVentaService{
    @Autowired
    private ArticuloVentaRepository modelRepository;

    @Autowired
    private ILineaService lineaService;

    private static final Logger logger = LoggerFactory.getLogger(ArticuloVentaService.class);


    @Override
    public List<ArticuloVentaDTO> listar() {
        List<ArticuloVenta> list = modelRepository.buscarNoEliminados();

        return convertListOfArticulesToDTO(list);
    }

    @Override
    public ArticuloVenta buscarPorId(Integer id) {
        ArticuloVenta articuloVenta = modelRepository.findById(id).orElse(null);

        if(articuloVenta == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }

        return articuloVenta;
    } 

    @Override
    public ArticuloVenta guardar(ArticuloVentaDTO model) {
        Integer idLinea = model.linea;

        ArticuloVenta newModel =  new ArticuloVenta();
        newModel.setDenominacion(model.denominacion);
        newModel.setLinea(lineaService.buscarPorId(idLinea));

        return modelRepository.save(newModel);
    }

    @Override
    public void eliminar(ArticuloVenta model) {
        model.asEliminado();
        modelRepository.save(model);
    }

    @Override
    public List<ArticuloVentaDTO> listar(String consulta) {
        List<ArticuloVenta> list = modelRepository.buscarNoEliminados(consulta);

        return convertListOfArticulesToDTO(list);
    }

    @Override
    public Page<ArticuloVenta> getArticulos(Pageable pageable) {
        return  modelRepository.findAll(pageable);
    }

    @Override
    public Page<ArticuloVentaDTO> findPaginated(Pageable pageable, List<ArticuloVentaDTO> list) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ArticuloVentaDTO> listado;
        if (list.size() < startItem) {
            listado = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, list.size());
            listado = list.subList(startItem, toIndex);
        }

        Page<ArticuloVentaDTO> bookPage
                = new PageImpl<ArticuloVentaDTO>(list, PageRequest.of(currentPage, pageSize), listado.size());

        return bookPage;
    }
    
    @Override
    public ArticuloVentaDTO update(ArticuloVentaDTO modelRecibido, ArticuloVenta model) {
        model.setDenominacion(modelRecibido.denominacion);
        model.setLinea(lineaService.buscarPorId(modelRecibido.linea));

        return new ArticuloVentaDTO(model);
    }

    private List<ArticuloVentaDTO> convertListOfArticulesToDTO(List<ArticuloVenta> list) {
        List<ArticuloVentaDTO> result = new ArrayList<>();
        
        list.forEach((model) -> {
            result.add(new ArticuloVentaDTO(model));
        });

        return result;
    }

    

}
