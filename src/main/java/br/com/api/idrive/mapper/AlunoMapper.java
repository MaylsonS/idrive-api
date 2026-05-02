package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.Aluno.AlunoRegistroDTO;
import br.com.api.idrive.domain.dto.Aluno.AlunoResponseDTO;
import br.com.api.idrive.domain.model.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "usuario.nome", source = "nome")
    @Mapping(target = "usuario.cpf", source = "cpf")
    @Mapping(target = "usuario.email", source = "email")
    @Mapping(target = "usuario.senha", source = "senha")
    @Mapping(target = "usuario.telefone", source = "telefone")
    @Mapping(target = "usuario.tipoPerfil", source = "tipoPerfil")
    Aluno toEntity(AlunoRegistroDTO dto);

    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "tipoPerfil", source = "usuario.tipoPerfil")
    AlunoResponseDTO toResponseDTO(Aluno aluno);
}
