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
import java.util.UUID;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaServiceImplement aulaService;

    public AulaController(AulaServiceImplement aulaService) {
        this.aulaService = aulaService;
    }

    @PostMapping("/criar-anuncio")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<AulaResponseDTO> criarAula(@RequestBody @Valid AulaRequestDTO dto, Authentication authentication) {

        String emailInstrutorLogado = authentication.getName();

        AulaResponseDTO response = aulaService.anuncioAula(dto, emailInstrutorLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/minhas-aulas")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<List<AulaResponseDTO>> minhasAulas(Authentication authentication) {
        String emailLogado = authentication.getName();
        List<AulaResponseDTO> response = aulaService.listarAulas(emailLogado);
        return ResponseEntity.ok(response);

    }

    @GetMapping ("/anuncios")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<List<AulaResponseDTO>> anunciosPublicos(Authentication authentication) {

        String emailLogado = authentication.getName();
        List<AulaResponseDTO> response = aulaService.listarAnunciosPublicos(emailLogado);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/editar-anuncio/{id}")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<AulaResponseDTO> editarAnuncio(@PathVariable UUID id, @RequestBody @Valid AulaRequestDTO dto, Authentication authentication) {

        String emailLogado = authentication.getName();
        AulaResponseDTO response = aulaService.editarAnuncio(id, dto, emailLogado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/excluir-anuncio/{id}")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<Void> excluirAnuncio(@PathVariable UUID id, Authentication authentication) {

        String emailLogado = authentication.getName();
        aulaService.excluirAnuncio(id, emailLogado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/instrutor/{instrutorId}")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<List<AulaResponseDTO>> listarPorInstrutor(
            @PathVariable UUID instrutorId) {
        return ResponseEntity.ok(aulaService.listarAulasPorInstrutor(instrutorId));
    }

    @PutMapping("/{id}/aceitar/{interessadoId}")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<AulaResponseDTO> aceitarAula(
            @PathVariable UUID id,
            @PathVariable UUID interessadoId,
            Authentication authentication) {
        return ResponseEntity.ok(aulaService.aceitarAula(id, interessadoId, authentication.getName()));
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('INSTRUTOR', 'ALUNO')")
    public ResponseEntity<AulaResponseDTO> cancelarAula(
            @PathVariable UUID id,
            Authentication authentication) {
        return ResponseEntity.ok(aulaService.cancelarAula(id, authentication.getName()));
    }

}