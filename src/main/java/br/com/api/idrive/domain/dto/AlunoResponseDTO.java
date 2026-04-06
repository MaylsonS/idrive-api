package br.com.api.idrive.domain.dto;

import java.util.UUID;

public record AlunoResponseDTO(
    UUID id,
    String nome,
    String email,
    String tipoPerfil
) {}
