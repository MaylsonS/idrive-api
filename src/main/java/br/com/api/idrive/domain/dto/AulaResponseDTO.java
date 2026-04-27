package br.com.api.idrive.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AulaResponseDTO(
        UUID id,
        LocalDateTime inicio,
        LocalDateTime fim,
        Double valor,
        String descricao,
        String nomeInstrutor
) {}