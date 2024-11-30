package jsges.nails.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jsges.nails.DTO.ArticuloVentaDTO;
import jsges.nails.domain.ArticuloVenta;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.IArticuloVentaService;
import lombok.NoArgsConstructor;


@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
@NoArgsConstructor
public class ArticuloVentaController {

    private static final Logger logger = LoggerFactory.getLogger(ArticuloVentaController.class);

    @Autowired
    private IArticuloVentaService modelService;


    @GetMapping({"/articulos"})
    public ResponseEntity<List<ArticuloVentaDTO>> getAll() {

        return ResponseEntity.ok(modelService.listar());
    }

    @GetMapping({"/articulosPageQuery"})
    public ResponseEntity<Page<ArticuloVentaDTO>> getItems(@RequestParam(defaultValue = "") String consulta, @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "${max_page}") int size) {
        
        Page<ArticuloVentaDTO> bookPage = modelService
            .findPaginated(PageRequest.of(page, size), modelService.listar(consulta));

        return ResponseEntity.ok(bookPage);
    }

    @PostMapping("/articulos")
    public ResponseEntity<?> agregar(@RequestBody ArticuloVentaDTO model) {
        ArticuloVenta result;
        
        try {
            result = modelService.guardar(model);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
        logger.info("Articulo guardado con exito");
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/articuloEliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        ArticuloVenta model;

        try {
            model = modelService.buscarPorId(id);
            modelService.eliminar(model);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        logger.info(String.format("Articulo con id %o eliminado con exito", id));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/articulos/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id) {
        ArticuloVenta articuloVenta;
            
        try {
            articuloVenta = modelService.buscarPorId(id);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
        
        return ResponseEntity.ok(new ArticuloVentaDTO(articuloVenta));
    }

    @PutMapping("/articulos/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                    @RequestBody ArticuloVentaDTO modelRecibido) {

        ArticuloVenta model;   
                                                     
        try {
            model = modelService.buscarPorId(id);
            modelService.guardar(modelService.update(modelRecibido, model));
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }                          

        logger.info(String.format("Articulo con id %o actualizado con exito", id));
        return ResponseEntity.ok(model);
    }

}
