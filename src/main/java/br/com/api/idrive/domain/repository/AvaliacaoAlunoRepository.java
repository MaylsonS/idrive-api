package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.AvaliacaoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AvaliacaoAlunoRepository extends JpaRepository<AvaliacaoAluno, UUID> {

    boolean existsByAulaId(UUID aulaId);

    @Query("SELECT AVG(a.media) FROM AvaliacaoAluno a WHERE a.aluno.id = :alunoId")
    Double calcularMediaPorAluno(@Param("alunoId") UUID alunoId);
}