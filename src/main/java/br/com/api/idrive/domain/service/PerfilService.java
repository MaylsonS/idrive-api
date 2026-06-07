package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.perfil.PerfilPublicoResponseDTO;
import br.com.api.idrive.domain.model.Usuario;

import java.util.UUID;

public interface PerfilService {
     PerfilPublicoResponseDTO buscarMeuPerfil(String email);
     PerfilPublicoResponseDTO buscarPorId(UUID id);
}
