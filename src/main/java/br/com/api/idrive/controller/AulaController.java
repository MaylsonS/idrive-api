package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.service.AulaServiceImplement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaServiceImplement aulaService;

    public AulaController(AulaServiceImplement aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaRequestDTO dto, Authentication authentication) {

        String emailInstrutorLogado = authentication.getName();

        AulaResponseDTO response = aulaService.criarAula(dto, emailInstrutorLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}