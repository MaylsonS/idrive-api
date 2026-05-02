package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;

public interface AulaService {
    public AulaResponseDTO anuncioAula(AulaRequestDTO dto, String email);
}
