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

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaServiceImplement aulaService;

    public AulaController(AulaServiceImplement aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping("/criar-anuncio")
    @PreAuthorize("hasRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaRequestDTO dto, Authentication authentication) {

        String emailInstrutorLogado = authentication.getName();

        AulaResponseDTO response = aulaService.anuncioAula(dto, emailInstrutorLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/minhas-aulas")
    @PreAuthorize("hasRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<List<AulaResponseDTO>> minhasAulas(Authentication authentication) {
        String emailLogado = authentication.getName();
        List<AulaResponseDTO> response = aulaService.listarAulas(emailLogado);
        return ResponseEntity.ok(response);

    }
}