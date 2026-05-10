package br.com.api.idrive.domain.dto.avaliacao;

import java.util.UUID;

public record AvaliacaoInstrutorResponseDTO(
        UUID id,
        UUID aulaId,
        String nomeInstrutor,
        Integer pontualidade,
        Integer seguirRegrasDeTransito,
        Integer clareza,
        Double media
) {}