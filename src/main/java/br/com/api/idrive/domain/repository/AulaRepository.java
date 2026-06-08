package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.Aula;
import br.com.api.idrive.domain.model.StatusAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AulaRepository extends JpaRepository<Aula, UUID> {

    List<Aula> findByAlunoId(UUID alunoId);

    List<Aula> findByInstrutorId(UUID instrutorId);

    List<Aula> findByInstrutorIdAndStatusIn(UUID instrutorId, List<StatusAula> statuses);

    List<Aula> findByInstrutorUsuarioEmail(String email);

    // O Spring faz o JOIN: Aula -> Aluno -> Usuario -> Email automaticamente
    List<Aula> findByAlunoUsuarioEmail(String email);

    // Aluno vê → anúncios criados por instrutores (instrutor não nulo) com status ABERTA
    List<Aula> findByInstrutorIsNotNullAndStatus(StatusAula status);

    // Instrutor vê → anúncios criados por alunos (aluno não nulo) com status ABERTA
    List<Aula> findByAlunoIsNotNullAndStatus(StatusAula status);

    // Traz apenas os anuncios com a data de inicio futiura
    List<Aula> findByInstrutorIsNotNullAndStatusAndInicioAfter(
            StatusAula status, LocalDateTime agora);

    List<Aula> findByAlunoIsNotNullAndStatusAndInicioAfter(
            StatusAula status, LocalDateTime agora);

    // busca aulas abertas que o inicio já passou
    @Query("SELECT a FROM Aula a WHERE a.status = :status AND a.inicio < :agora")
    List<Aula> findByStatusAndInicioAfterIsFalse(
            @Param("status") StatusAula status,
            @Param("agora") LocalDateTime agora);
}