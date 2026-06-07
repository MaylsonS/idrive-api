package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.dto.Instrutor.InstrutorRegistroDTO;
import br.com.api.idrive.domain.model.Instrutor;

import java.util.List;

public interface InstrutorService {
    Instrutor registrar(InstrutorRegistroDTO dto);
    List<AulaResponseDTO> listarAulas();
}
