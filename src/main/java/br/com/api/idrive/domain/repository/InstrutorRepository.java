package br.com.api.idrive.domain.repository;

import br.com.api.idrive.domain.model.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, UUID> {

}