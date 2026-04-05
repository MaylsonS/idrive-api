package br.com.api.idrive.domain.dto;

import java.util.UUID;

public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        String tipoPerfil
) {
}