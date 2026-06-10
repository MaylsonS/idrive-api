package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoResponseDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorResponseDTO;

public interface AvaliacaoService {

    AvaliacaoInstrutorResponseDTO avaliarInstrutor(AvaliacaoInstrutorRequestDTO dto, String emailLogado);

    AvaliacaoAlunoResponseDTO avaliarAluno(AvaliacaoAlunoRequestDTO dto, String emailLogado);
}
