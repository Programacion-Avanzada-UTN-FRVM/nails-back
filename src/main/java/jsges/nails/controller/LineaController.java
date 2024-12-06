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

import jsges.nails.domain.Linea;
import jsges.nails.dto.LineaDTO;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.ILineaService;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
@NoArgsConstructor
public class LineaController {
   
    private static final Logger log = LoggerFactory.getLogger(LineaController.class);
   
    @Autowired
    private ILineaService modelService;

    @GetMapping({"/lineas"})
    public ResponseEntity<List<LineaDTO>> getAll() {
        return ResponseEntity.ok(modelService.listar());
    }

    @GetMapping({"/lineasPageQuery"})
    public ResponseEntity<Page<LineaDTO>> getItems(@RequestParam(defaultValue = "") String consulta, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "${max_page}") int size) {
        
         Page<LineaDTO> bookPage = modelService
            .findPaginated(PageRequest.of(page, size), modelService.listar(consulta));

        return ResponseEntity.ok(bookPage);
    }

    @PostMapping("/linea")
    public  ResponseEntity<?> agregar(@RequestBody LineaDTO model) {
        
        Linea result;
        
        try {
            result = modelService.newModel(model);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        log.info("Linea guardada con exito");
        return ResponseEntity.ok(result);
    }

    @PutMapping("/lineaEliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
       
        Linea model;

        try {
            model = modelService.buscarPorId(id);
            modelService.eliminar(model);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        log.info(String.format("Articulo con id %o eliminado con exito", id));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/linea/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        Linea linea;
            
        try {
            linea = modelService.buscarPorId(id);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
        
        return ResponseEntity.ok(new LineaDTO(linea));
    }

    @PutMapping("/linea/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody LineaDTO modelRecibido) {
        Linea model;   
                                                     
        try {
            model = modelService.buscarPorId(id);
            modelService.update(modelRecibido, model);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }                          

        log.info(String.format("Linea con id %o actualizado con exito", id));
        return ResponseEntity.ok(model);
    }

}

