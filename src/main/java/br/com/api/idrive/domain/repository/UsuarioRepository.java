package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}