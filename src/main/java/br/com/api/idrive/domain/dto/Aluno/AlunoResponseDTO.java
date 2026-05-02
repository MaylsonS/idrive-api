package br.com.api.idrive.domain.dto.Aluno;

import java.util.UUID;

public record AlunoResponseDTO(
    UUID id,
    String nome,
    String email,
    String tipoPerfil
) {}
