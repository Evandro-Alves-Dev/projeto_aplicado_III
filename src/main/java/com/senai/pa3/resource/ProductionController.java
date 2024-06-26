package com.senai.pa3.resource;

import com.senai.pa3.dto.ProductionDTO;
import com.senai.pa3.services.ProductionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/production", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_PDF_VALUE})
public class ProductionController {

    private static final Logger LOGGER = Logger.getLogger(ProductionController.class.getName());
    private ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public ResponseEntity<List<ProductionDTO>> findAll() {
        LOGGER.info("Iniciado a busca de todos os apontamento de produção");
        var response = productionService.findAll();
        LOGGER.info("Finalizado a busca de todos os apontamento de produção");
        return ResponseEntity.ok().body(response);
    }

//    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<InputStreamResource> findAll2() {
//        LOGGER.info("Iniciado a busca de todos os apontamentos de produção");
//        var response = productionService.findAll2();
//        LOGGER.info("Finalizado a busca de todos os apontamentos de produção");
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(response));
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductionDTO> findById(@PathVariable Long id) {
        LOGGER.info("Iniciado a busca do apontamento de produção por ID");
        var response = productionService.findById(id);
        LOGGER.info("Finalizado a busca do apontamento de produção por ID");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ProductionDTO> insert(@Valid @RequestBody ProductionDTO productionDTO) {
        LOGGER.info("Iniciado a inserção de um novo apontamento de produção");
        var response = productionService.insert(productionDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.getIdProduction()).toUri();
        LOGGER.info("Finalizado a inserção de um novo apontamento de produção");
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductionDTO> update(@PathVariable Long id, @Valid @RequestBody ProductionDTO productionDTO) {
        LOGGER.info("Iniciado a atualização de um apontamento de produção");
        var response = productionService.update(id, productionDTO);
        LOGGER.info("Finalizado a atualização de um apontamento de produção");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("Iniciado a exclusão de apontamento de produção");
        productionService.delete(id);
        LOGGER.info("Finalizado a exclusão de um apontamento de produção");
        return ResponseEntity.noContent().build();
    }
}
