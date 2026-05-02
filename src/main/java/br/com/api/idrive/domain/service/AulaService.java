package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;

import java.util.List;

public interface AulaService {
    public AulaResponseDTO anuncioAula(AulaRequestDTO dto, String email);
    public List<AulaResponseDTO> listarAulas(String email);
}
