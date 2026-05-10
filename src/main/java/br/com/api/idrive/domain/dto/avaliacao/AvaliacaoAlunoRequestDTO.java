package br.com.api.idrive.domain.dto.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

// enviado pelo instrutor para avaliar
public record AvaliacaoAlunoRequestDTO(

        @NotNull(message = "O id da aula é obrigatório")
        UUID aulaId,

        @NotNull @Min(1) @Max(5)
        Integer pontualidade,

        @NotNull @Min(1) @Max(5)
        Integer receptividade
) {}