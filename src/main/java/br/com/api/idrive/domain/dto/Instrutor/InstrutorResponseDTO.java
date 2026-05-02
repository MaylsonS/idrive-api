package br.com.api.idrive.domain.dto.Instrutor;

import java.util.UUID;

public record InstrutorResponseDTO(
       UUID id,
       String nome,
       String email,
       String cnh,
       String tipoPerfil
) {}
