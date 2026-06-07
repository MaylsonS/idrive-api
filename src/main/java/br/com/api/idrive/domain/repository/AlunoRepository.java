package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    Optional<Aluno> findByUsuarioCpf(String cpf);

    Optional<Aluno> findByUsuarioEmail(String email);
}
