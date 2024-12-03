package jsges.nails.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jsges.nails.DTO.ClienteDTO;
import jsges.nails.domain.Cliente;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.IClienteService;
import lombok.NoArgsConstructor;



@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
@NoArgsConstructor
public class ClienteControlador {

    private static final Logger log = LoggerFactory.getLogger(ClienteControlador.class);
    
    @Autowired
    private IClienteService clienteServicio;

    @GetMapping({"/clientes"})
    public ResponseEntity<List<ClienteDTO>> getAll() {

        return ResponseEntity.ok(clienteServicio.listar()); 
    }

    @GetMapping({"/clientesPageQuery"})
    public ResponseEntity<Page<ClienteDTO>> getItems(@RequestParam(defaultValue = "") String consulta,@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "${max_page}") int size) {

        Page<ClienteDTO> bookPage = clienteServicio
        .findPaginated(PageRequest.of(page, size), clienteServicio.listar());

        return ResponseEntity.ok().body(bookPage);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> agregar(@RequestBody Cliente cliente) {
        Cliente result;
        
        try {
            result = clienteServicio.guardar(cliente);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/clienteEliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Cliente cliente;

        try {
            cliente = clienteServicio.buscarPorId(id);
            clienteServicio.eliminar(cliente);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        log.info(String.format("Cliente con id %o eliminado con exito", id));
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        Cliente cliente;
        
         try {
            cliente = clienteServicio.buscarPorId(id);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
        
        return ResponseEntity.ok(new ClienteDTO(cliente));

    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                              @RequestBody ClienteDTO modelRecibido) {

       Cliente cliente;                                                
    
       try {
            cliente = clienteServicio.buscarPorId(id);
            clienteServicio.update(modelRecibido, cliente);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }                                  

        log.info(String.format("Cliente con id %o actualizado con exito", id));
        return ResponseEntity.ok(cliente);
    }

}
