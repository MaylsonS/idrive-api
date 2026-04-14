package br.com.api.idrive.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AulaRequestDTO(
        @NotNull(message = "O horário de início é obrigatório")
        LocalDateTime inicio,

        @NotNull(message = "O horário de término é obrigatório")
        LocalDateTime fim,

        @NotNull(message = "O valor da aula é obrigatório")
        Double valor,

        String descricao
) {}