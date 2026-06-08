package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AulaService {
    AulaResponseDTO anuncioAula(AulaRequestDTO dto, String email);
    List<AulaResponseDTO> listarAulas(String email);
    List<AulaResponseDTO> listarAnunciosPublicos(String emailLogado);
    AulaResponseDTO editarAnuncio(UUID id, AulaRequestDTO dto, String emailLogado);
    void excluirAnuncio(UUID id, String emailLogado);
    List<AulaResponseDTO> listarAulasPorInstrutor(UUID instrutorId);
}
