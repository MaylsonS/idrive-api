package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.model.Aula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AulaMapper {

    AulaResponseDTO toResponseDTO(Aula aula);

    Aula toEntity(AulaRequestDTO dto);
}