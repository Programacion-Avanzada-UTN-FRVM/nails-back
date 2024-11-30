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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jsges.nails.DTO.ServicioDTO;
import jsges.nails.domain.ItemServicio;
import jsges.nails.domain.Servicio;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.IItemServicioService;
import jsges.nails.service.IServicioService;
import jsges.nails.service.ITipoServicioService;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
@NoArgsConstructor
public class ServicioController {

    private static final Logger logger = LoggerFactory.getLogger(ServicioController.class);

    @Autowired
    private IServicioService modelService;
    
    @Autowired
    private IItemServicioService itemServicioService;


    @GetMapping({"/servicios"})
    public ResponseEntity<List<ServicioDTO>> getAll() {

        return ResponseEntity.ok(modelService.listar());
    }
    
    @GetMapping("/servicio/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        Servicio model;
        List<ItemServicio>listItems;
        try {
            model = modelService.buscarPorId(id);
            listItems = itemServicioService.buscarPorServicio(model.getId());
        } catch (RecursoNoEncontradoExcepcion e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        return ResponseEntity.ok(new ServicioDTO(model, listItems));
    }

    @GetMapping({"/serviciosPageQuery"})
    public ResponseEntity<Page<ServicioDTO>> getItems(@RequestParam(defaultValue = "") String consulta, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "${max_page}") int size) {
        
        Page<ServicioDTO> bookPage = modelService.findPaginated(PageRequest.of(page, size),modelService.listar(consulta));

        return ResponseEntity.ok().body(bookPage);
    }

    @PostMapping("/servicios")
    public ResponseEntity<?> agregar(@RequestBody ServicioDTO model){
        ServicioDTO servicioGuardado;

        try {
            servicioGuardado = modelService.guardar(model);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        return ResponseEntity.ok(servicioGuardado);
    }
}

