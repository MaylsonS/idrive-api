package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.AvaliacaoInstrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface AvaliacaoInstrutorRepository extends JpaRepository<AvaliacaoInstrutor, UUID> {

    boolean existsByAulaId(UUID aulaId);

    @Query("SELECT AVG(a.media) FROM AvaliacaoInstrutor a WHERE a.instrutor.id = :instrutorId")
    Double calcularMediaPorInstrutor(@Param("instrutorId") UUID instrutorId);
}