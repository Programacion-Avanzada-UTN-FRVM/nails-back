package jsges.nails.service.impl;

import jsges.nails.DTO.ArticuloVentaDTO;
import jsges.nails.DTO.LineaDTO;
import jsges.nails.domain.ArticuloVenta;
import jsges.nails.domain.Linea;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.LineaRepository;
import jsges.nails.service.ILineaService;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LineaService implements ILineaService {

    @Autowired
    private LineaRepository modelRepository;
    private static final Logger logger = LoggerFactory.getLogger(LineaService.class);

    @Override
    public List<LineaDTO> listar() {
        List<Linea> list = modelRepository.buscarNoEliminados();

        return convertListOfLineaToDTO(list);
    }

    @Override
    public Linea buscarPorId(Integer id) {
        Linea linea = modelRepository.findById(id).orElse(null);

        if(linea == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }
        
        return linea;
    }

    @Override
    public Linea guardar(Linea model) {
        return modelRepository.save(model);
    }

    @Override
    public Linea newModel(LineaDTO modelDTO) {
        Linea model =  new Linea(modelDTO);
        return guardar(model);
    }

    @Override
    public void eliminar(Linea model) {
        model.asEliminado();
        modelRepository.save(model);
    }

    @Override
    public List<LineaDTO> listar(String consulta) {
        List<Linea> list = modelRepository.buscarNoEliminados(consulta);

        return convertListOfLineaToDTO(list);
    }

    @Override
    public Page<Linea> getLineas(Pageable pageable) {
        return  modelRepository.findAll(pageable);
    }

    public List<Linea> buscar(String consulta) {
        List<Linea> list = modelRepository.buscarExacto(consulta);

         if (!list.isEmpty()){
            throw new RecursoNoEncontradoExcepcion("Error con la consulta");
        }
        
        return list;
    }

    @Override
    public Page<LineaDTO> findPaginated(Pageable pageable, List<LineaDTO>lineas) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<LineaDTO> list;
        if (lineas.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lineas.size());
            list = lineas.subList(startItem, toIndex);
        }

        Page<LineaDTO> bookPage
                = new PageImpl<LineaDTO>(list, PageRequest.of(currentPage, pageSize), lineas.size());

        return bookPage;
    }

    @Override
    public Linea update(LineaDTO modelRecibido, Linea model) {
        model.setDenominacion(modelRecibido.denominacion);

        return model;
    }

    private List<LineaDTO> convertListOfLineaToDTO(List<Linea> list) {
        List<LineaDTO> result = new ArrayList<>();
        
        list.forEach((model) -> {
            result.add(new LineaDTO(model));
        });

        return result;
    }

}
