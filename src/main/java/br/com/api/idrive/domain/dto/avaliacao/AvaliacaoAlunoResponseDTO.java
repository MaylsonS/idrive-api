package br.com.api.idrive.domain.dto.avaliacao;

import java.util.UUID;

public record AvaliacaoAlunoResponseDTO(
        UUID id,
        UUID aulaId,
        String nomeAluno,
        Integer pontualidade,
        Integer receptividade,
        Double media
) {}