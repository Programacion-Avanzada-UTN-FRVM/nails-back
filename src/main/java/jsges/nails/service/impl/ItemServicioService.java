package jsges.nails.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jsges.nails.domain.ItemServicio;
import jsges.nails.repository.ItemServicioRepository;
import jsges.nails.service.IItemServicioService;


@Service
public class ItemServicioService implements IItemServicioService {


    @Autowired
    private ItemServicioRepository modelRepository;
    private final Logger log = LoggerFactory.getLogger(ItemServicioService.class);

    @Override
    public List<ItemServicio> listar() {
        List<ItemServicio> items = modelRepository.findAll();
        log.info("Items desde el repositorio: " + items);

        return items; //devolver lsita de ItemServicioDTO ?!?!?!?
    }

    @Override
    public ItemServicio buscarPorId(Integer id) {
        return null;
    }

    @Override
    public ItemServicio guardar(ItemServicio model) {
        return modelRepository.save(model);
    }

    @Override
    public Page<ItemServicio> findPaginated(Pageable pageable, List<ItemServicio> servicios) {
        return null;
    }

    @Override
    public Page<ItemServicio> getItemServicios(Pageable pageable) {
        return null;
    }
    @Override
    public List<ItemServicio> buscarPorServicio(Integer idServicio){

        return modelRepository.buscarPorServicio(idServicio);
    };
}
