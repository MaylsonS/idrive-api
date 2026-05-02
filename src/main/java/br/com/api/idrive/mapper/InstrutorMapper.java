package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.Instrutor.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.Instrutor.InstrutorResponseDTO;
import br.com.api.idrive.domain.model.Instrutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstrutorMapper {

    @Mapping(target = "usuario.nome", source = "nome")
    @Mapping(target = "usuario.cpf", source = "cpf")
    @Mapping(target = "usuario.email", source = "email")
    @Mapping(target = "usuario.senha", source = "senha")
    @Mapping(target = "usuario.telefone", source = "telefone")
    @Mapping(target = "usuario.tipoPerfil", source = "tipoPerfil")
    Instrutor toEntity(InstrutorRegistroDTO dto);

    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "tipoPerfil", source = "usuario.tipoPerfil")
    InstrutorResponseDTO toResponseDTO(Instrutor instrutor);
}
