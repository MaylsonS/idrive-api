package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorResponseDTO;
import br.com.api.idrive.domain.model.AvaliacaoInstrutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AvaliacaoInstrutorMapper {


    @Mapping(target = "aulaId", source = "aula.id")
    @Mapping(target = "nomeInstrutor", source = "instrutor.usuario.nome")

    AvaliacaoInstrutorResponseDTO toResponseDTO(AvaliacaoInstrutor avaliacao);



}
