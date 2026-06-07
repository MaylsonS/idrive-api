package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.perfil.PerfilPublicoResponseDTO;
import br.com.api.idrive.domain.service.PerfilService;
import br.com.api.idrive.domain.service.PerfilServiceImplement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    private final PerfilServiceImplement perfilServiceImplement;

    public PerfilController(PerfilServiceImplement perfilServiceImplement) {
        this.perfilServiceImplement = perfilServiceImplement;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<PerfilPublicoResponseDTO> meuPerfil(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(perfilServiceImplement.buscarMeuPerfil(email));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<PerfilPublicoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(perfilServiceImplement.buscarPorId(id));
    }
}