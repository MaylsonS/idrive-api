package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AulaRepository extends JpaRepository<Aula, UUID> {

    List<Aula> findByAlunoId(UUID alunoId);

    List<Aula> findByInstrutorId(UUID instrutorId);
}