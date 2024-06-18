package com.senai.pa3.resource;

import com.senai.pa3.dto.UserDTO;
import com.senai.pa3.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        LOGGER.info("Iniciado a busca de todod os usuarios");
        var response = userService.findAll();
        LOGGER.info("Finalizado a busca de todos os usuarios");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        LOGGER.info("Iniciado a busca do usuario por ID");
        var response = userService.findById(id);
        LOGGER.info("Finalizado a busca do usuario por ID");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO userDTO) {
        LOGGER.info("Iniciado a inserção de um novo usuario");
        var response = userService.insert(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(response.getId()).toUri();
        LOGGER.info("Finalizado a inserção de um novo usuario");
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        LOGGER.info("Iniciado a atualização de um usuario");
        var response = userService.update(id, userDTO);
        LOGGER.info("Finalizado a atualização de um usuario");
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("Iniciado a exclusão de usuario");
        userService.delete(id);
        LOGGER.info("Finalizado a exclusão de um usuario");
        return ResponseEntity.noContent().build();
    }
}
