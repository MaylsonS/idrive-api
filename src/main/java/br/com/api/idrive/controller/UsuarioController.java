package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.token.LoginDTO;
import br.com.api.idrive.domain.dto.token.TokenDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioResponseDTO;
import br.com.api.idrive.domain.infra.security.TokenService;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.service.UsuarioServiceImplement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioServiceImplement userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UsuarioController(UsuarioServiceImplement service, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userService = service;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        var userAuthToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authenticationManager.authenticate(userAuthToken);

        var token = tokenService.generateToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioRegistroDTO dto) {
        userService.registar(dto);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> users = userService.listarTodos();
        return ResponseEntity.ok(users);
    }
}