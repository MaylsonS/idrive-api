package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.AulaRequestDTO;
import br.com.api.idrive.domain.dto.AulaResponseDTO;
import br.com.api.idrive.domain.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
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