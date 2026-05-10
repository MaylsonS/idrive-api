package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoResponseDTO;
import br.com.api.idrive.domain.model.AvaliacaoAluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvaliacaoAlunoMapper {

    @Mapping(target = "aulaId", source = "aula.id")
    @Mapping(target = "nomeAluno", source = "aluno.usuario.nome")

    AvaliacaoAlunoResponseDTO toResponseDTO(AvaliacaoAluno avaliacao);

}
