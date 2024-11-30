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

import jsges.nails.DTO.TipoServicioDTO;
import jsges.nails.domain.TipoServicio;
import jsges.nails.excepcion.RecursoNoEncontradoExcepcion;
import jsges.nails.service.ITipoServicioService;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value="${path_mapping}")
@CrossOrigin(value="${path_cross}")
@NoArgsConstructor
public class TipoServicioController {

    private static final Logger log = LoggerFactory.getLogger(TipoServicioController.class);
   
    @Autowired
    private ITipoServicioService modelService;

    @GetMapping({"/tiposServicios"})
    public ResponseEntity<List<TipoServicio>> getAll() {

        return ResponseEntity.ok(modelService.listar());
    }

    @GetMapping({"/tiposServiciosPageQuery"})
    public ResponseEntity<Page<TipoServicio>> getItems(@RequestParam(defaultValue = "") String consulta, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "${max_page}") int size) {
        
        List<TipoServicio> listado = modelService.listar(consulta);
        Page<TipoServicio> bookPage = modelService.findPaginated(PageRequest.of(page, size),listado);

        return ResponseEntity.ok().body(bookPage);
    }


    @PostMapping("/tiposServicios")
    public  ResponseEntity<TipoServicio>  agregar(@RequestBody TipoServicioDTO model){
        TipoServicio nuevoModelo = modelService.newModel(model);

        log.info(String.format("Se agrego el Tipo de Servicio con id %o", nuevoModelo.getId()));
        return ResponseEntity.ok(nuevoModelo);
    }

    @PutMapping("/tipoServicioEliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id){
        TipoServicio model;
        
        try {
            model = modelService.buscarPorId(id);
            modelService.eliminar(model);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        log.info(String.format("Se elimino el Tipo de Servicio con id %d", model.getId()));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/tiposServicios/{id}")
    public ResponseEntity<?> getPorId(@PathVariable Integer id){
        TipoServicio model;
        
        try {
            model = modelService.buscarPorId(id);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }

        return ResponseEntity.ok(model);
    }

    @PutMapping("/tiposServicios/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                   @RequestBody TipoServicioDTO modelRecibido){
        TipoServicio model;

        try {
            model = modelService.buscarPorId(id);
            modelService.update(modelRecibido, model);
        } catch (RecursoNoEncontradoExcepcion notFound) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e);
        }
                                                  
        log.info(String.format("Se actualizo el Tipo de Servicio con id %d", model.getId()));
        return ResponseEntity.ok(modelRecibido);
    }

}
