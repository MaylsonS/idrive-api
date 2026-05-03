package br.com.api.idrive.domain.dto.Aula;

import br.com.api.idrive.domain.model.StatusAula;
import java.time.LocalDateTime;
import java.util.UUID;

public record AulaResponseDTO(
        UUID id,
        LocalDateTime inicio,
        LocalDateTime fim,
        Double valor,
        String descricao,
        String autor,
        String coAutor,
        StatusAula status
) {}