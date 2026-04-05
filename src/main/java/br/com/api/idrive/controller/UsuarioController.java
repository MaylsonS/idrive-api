package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService userService;

    public UsuarioController(UsuarioService service) {
        this.userService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioRegistroDTO dto) {
        userService.registrar(dto);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> users = userService.listarTodos();
        return ResponseEntity.ok(users);
    }
}