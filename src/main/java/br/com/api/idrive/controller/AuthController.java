package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.Instrutor.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.token.LoginDTO;
import br.com.api.idrive.domain.dto.token.TokenDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioRegistroDTO;
import br.com.api.idrive.domain.infra.security.TokenService;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.service.InstrutorServiceImplement;
import br.com.api.idrive.domain.service.UsuarioServiceImplement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final InstrutorServiceImplement instrutorService;
    private final UsuarioServiceImplement usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(InstrutorServiceImplement instrutorService, UsuarioServiceImplement usuarioService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.instrutorService = instrutorService;
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/register-instrutor")
    public ResponseEntity<String> registrar(@RequestBody @Valid InstrutorRegistroDTO dto) {
        instrutorService.registrar(dto);
        return ResponseEntity.ok("Instrutor cadastrado com sucesso!");
    }

    @PostMapping("/register-aluno")
    public ResponseEntity<String> registrar(@RequestBody @Valid UsuarioRegistroDTO dto) {
        usuarioService.registar(dto);
        return ResponseEntity.ok("Aluno cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        var userAuthToken = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authenticationManager.authenticate(userAuthToken);

        var token = tokenService.generateToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }
}