package jsges.nails.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jsges.nails.DTO.ClienteDTO;
import jsges.nails.domain.Cliente;

public interface IClienteService {
    public List<ClienteDTO> listar();

    public Cliente buscarPorId(Integer id);

    public Cliente guardar(Cliente cliente);

    public void eliminar(Cliente cliente);

      public List<Cliente> listar(String consulta);

    public Page<Cliente> getClientes(Pageable pageable);

    public Page<ClienteDTO> findPaginated(Pageable pageable, List<ClienteDTO> clientes);

    public ClienteDTO update(ClienteDTO modelRecibido, Cliente model);
}
