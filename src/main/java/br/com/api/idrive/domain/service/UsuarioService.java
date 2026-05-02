package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.usuario.UsuarioRegistroDTO;
import br.com.api.idrive.domain.model.Usuario;

public interface UsuarioService {
    public Usuario registar(UsuarioRegistroDTO dto);

}
