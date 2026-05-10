package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoResponseDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorResponseDTO;
import br.com.api.idrive.domain.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    // Aluno avalia o instrutor — só ALUNO pode chamar
    @PostMapping("/instrutor")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<AvaliacaoInstrutorResponseDTO> avaliarInstrutor(
            @RequestBody @Valid AvaliacaoInstrutorRequestDTO dto,
            Authentication authentication) {

        String emailLogado = authentication.getName();
        return ResponseEntity.ok(avaliacaoService.avaliarInstrutor(dto, emailLogado));
    }

    // Instrutor avalia o aluno — só INSTRUTOR pode chamar
    @PostMapping("/aluno")
    @PreAuthorize("hasRole('INSTRUTOR')")
    public ResponseEntity<AvaliacaoAlunoResponseDTO> avaliarAluno(
            @RequestBody @Valid AvaliacaoAlunoRequestDTO dto,
            Authentication authentication) {

        String emailLogado = authentication.getName();
        return ResponseEntity.ok(avaliacaoService.avaliarAluno(dto, emailLogado));
    }
}