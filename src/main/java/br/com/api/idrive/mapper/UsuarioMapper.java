package br.com.api.idrive.mapper;

import br.com.api.idrive.domain.dto.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioRegistroDTO dto);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
