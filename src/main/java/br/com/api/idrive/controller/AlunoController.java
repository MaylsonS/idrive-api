package br.com.api.idrive.controller;


import br.com.api.idrive.domain.dto.AlunoRegistroDTO;
import br.com.api.idrive.domain.dto.LoginDTO;
import br.com.api.idrive.domain.dto.TokenDTO;
import br.com.api.idrive.domain.infra.security.TokenService;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.service.AlunoService;
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
@RequestMapping("/aluno")
public class AlunoController {


    private final AlunoService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody @Valid AlunoRegistroDTO dto) {
        service.registrar(dto);
        return ResponseEntity.ok("Aluno Cadastrado com Sucesso");
    }

}
