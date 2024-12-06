package jsges.nails.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jsges.nails.domain.ItemServicio;
import jsges.nails.domain.Servicio;
import jsges.nails.domain.TipoServicio;
import jsges.nails.dto.ItemServicioDTO;
import jsges.nails.dto.ServicioDTO;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ServicioRepository;
import jsges.nails.service.IClienteService;
import jsges.nails.service.IItemServicioService;
import jsges.nails.service.IServicioService;
import jsges.nails.service.ITipoServicioService;

@Service
public class ServicioService implements IServicioService {

    private static final Logger logger = LoggerFactory.getLogger(ServicioService.class);

    @Autowired
    private ServicioRepository modelRepository;

    @Autowired
    private IItemServicioService itemServicioService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ITipoServicioService tipoServicioService;

    @Override
    public List<ServicioDTO> listar() {
        List<Servicio> servicios = modelRepository.buscarNoEliminados();
         List<ServicioDTO> lista =new ArrayList<>();
        for (Servicio elemento : servicios) {
            List<ItemServicio> items = itemServicioService.listar();
            ServicioDTO ser  = new ServicioDTO(elemento,items);
            lista.add(ser);
        }

        return lista;
    }

    @Override
    public Servicio buscarPorId(Integer id) {
        Servicio servicio = modelRepository.findById(id).orElse(null);

        if (servicio == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }

        return servicio;
    }

    @Override
    public ServicioDTO guardar(ServicioDTO model) {
        Integer idCliente = model.cliente;

        Servicio newModel =  new Servicio();
        newModel.setCliente(clienteService.buscarPorId(idCliente));
        newModel.setFechaRegistro(model.fechaDocumento);
        newModel.setFechaRealizacion(model.fechaDocumento);
        newModel.setEstado(0);

        Servicio servicioGuardado = modelRepository.save(newModel);

        for (ItemServicioDTO elemento : model.listaItems) {
            double precio = elemento.getPrecio();
            logger.info("entra for");

            TipoServicio tipoServicio = tipoServicioService.buscarPorId(elemento.getTipoServicioId());
            String observacion = elemento.getObservaciones();
            ItemServicio item = new ItemServicio(newModel, tipoServicio, precio,observacion);

            itemServicioService.guardar(item);

        }
        
        return new ServicioDTO(servicioGuardado);
    }

    @Override
    public Page<Servicio> getServicios(Pageable pageable) {
        return  modelRepository.findAll(pageable);
    }

    @Override
    public Page<ServicioDTO> findPaginated(Pageable pageable, List<ServicioDTO> listado) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ServicioDTO> list;
        if (listado.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, listado.size());
            list = listado.subList(startItem, toIndex);
        }

        Page<ServicioDTO> bookPage
                = new PageImpl<ServicioDTO>(list, PageRequest.of(currentPage, pageSize), listado.size());

        return bookPage;
    }

    @Override
    public List<ServicioDTO> listar(String consulta) {
        List<Servicio> list = modelRepository.buscarNoEliminados(consulta);

        return convertListOfServicioToDTO(list);
    }

    private List<ServicioDTO> convertListOfServicioToDTO(List<Servicio> list) {
        List<ServicioDTO> result = new ArrayList<>();
        
        list.forEach((model) -> {
            result.add(new ServicioDTO(model));
        });

        return result;
    }

}
