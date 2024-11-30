package jsges.nails.service.impl;

import jsges.nails.DTO.ArticuloVentaDTO;
import jsges.nails.DTO.TipoServicioDTO;
import jsges.nails.domain.ArticuloVenta;
import jsges.nails.domain.TipoServicio;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.TipoServicioRepository;
import jsges.nails.service.ITipoServicioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TipoServicioService implements ITipoServicioService {

    @Autowired
    private TipoServicioRepository modelRepository;
    private static final Logger logger = LoggerFactory.getLogger(TipoServicioService.class);

    @Override
    public List<TipoServicio> listar() {
        return modelRepository.buscarNoEliminados();
    }

    @Override
    public TipoServicio buscarPorId(Integer id) {
        TipoServicio tipoServicio = modelRepository.findById(id).orElse(null);

        if (tipoServicio == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontraron resultados");
        }

        return tipoServicio;
    }

    @Override
    public TipoServicio guardar(TipoServicio model) {
        return modelRepository.save(model);
    }


    @Override
    public TipoServicio newModel(TipoServicioDTO modelDTO) {
        TipoServicio model =  new TipoServicio();
        model.setDenominacion(modelDTO.denominacion);
        return guardar(model);
    }

    @Override
    public void eliminar(TipoServicio model) {
        model.setEstado(1);
        modelRepository.save(model);
    }

    @Override
    public List<TipoServicio> listar(String consulta) {
        //logger.info("service " +consulta);
        return modelRepository.buscarNoEliminados(consulta);
    }

    @Override
    public Page<TipoServicio> getTiposServicios(Pageable pageable) {
        return  modelRepository.findAll(pageable);
    }

    @Override
    public List<TipoServicio> buscar(String consulta) {
        List<TipoServicio> list =  modelRepository.buscarExacto(consulta);

        if (!list.isEmpty()){
            throw new RecursoNoEncontradoExcepcion("No se encontraron resultados");
        }

        return list;
    }

    @Override
    public TipoServicioDTO update(TipoServicioDTO modelRecibido, TipoServicio model){
        model.setDenominacion(modelRecibido.denominacion);
        guardar(model);

        return modelRecibido;
    }

    @Override
    public Page<TipoServicio> findPaginated(Pageable pageable, List<TipoServicio>lineas) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<TipoServicio> list;
        if (lineas.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lineas.size());
            list = lineas.subList(startItem, toIndex);
        }

        Page<TipoServicio> bookPage
                = new PageImpl<TipoServicio>(list, PageRequest.of(currentPage, pageSize), lineas.size());

        return bookPage;
    }

}
