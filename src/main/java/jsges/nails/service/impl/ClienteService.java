package jsges.nails.service.impl;

import jsges.nails.domain.ArticuloVenta;
import jsges.nails.domain.Cliente;
import jsges.nails.dto.ArticuloVentaDTO;
import jsges.nails.dto.ClienteDTO;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.repository.ClienteRepository;
import jsges.nails.service.IClienteService;
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

@Service
public class ClienteService implements IClienteService {
   
    @Autowired
    private ClienteRepository clienteRepository;
   
    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
   
    @Override
    public List<ClienteDTO> listar() {
        List<Cliente> list = clienteRepository.buscarNoEliminados();
        
        return convertListOfClienteToDTO(list);
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        Cliente model = clienteRepository.findById(id).orElse(null);

        if (model == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);
        }

        return model;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Cliente cliente) {
        cliente.setEstado(1);  
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> listar(String consulta) {
         return clienteRepository.buscarNoEliminados(consulta);
    }

    public Page<Cliente> getClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    public Page<ClienteDTO> findPaginated(Pageable pageable, List<ClienteDTO> clientes) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<ClienteDTO> list;
        if (clientes.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, clientes.size());
            list = clientes.subList(startItem, toIndex);
        }

        Page<ClienteDTO> bookPage
                = new PageImpl<ClienteDTO>(list, PageRequest.of(currentPage, pageSize), clientes.size());

        return bookPage;
    }

    @Override
    public ClienteDTO update(ClienteDTO modelRecibido, Cliente model) {
        model.setCelular(modelRecibido.getCelular());
        model.setContacto(modelRecibido.getContacto());
        model.setRazonSocial(modelRecibido.getRazonSocial());
        model.setLetra(modelRecibido.getLetra());
        model.setMail(modelRecibido.getMail());
        model.setFechaInicio(modelRecibido.getFechaInicio());
        model.setFechaNacimiento(modelRecibido.getFechaNacimiento());

        clienteRepository.save(model);

        return new ClienteDTO(model);
    }
    
    private List<ClienteDTO> convertListOfClienteToDTO(List<Cliente> list) {
        List<ClienteDTO> result = new ArrayList<>();
        
        list.forEach((model) -> {
            result.add(new ClienteDTO(model));
        });

        return result;
    }

}
