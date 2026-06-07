package br.com.api.idrive.domain.dto.perfil;

import java.util.UUID;

public record PerfilPublicoResponseDTO(
        UUID id,
        String nome,
        String tipoPerfil,
        Double notaMedia,
        String descricao,
        Integer totalAulas
) {}
