package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.AulaRequestDTO;
import br.com.api.idrive.domain.dto.AulaResponseDTO;
import br.com.api.idrive.domain.model.Aula;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.repository.AlunoRepository;
import br.com.api.idrive.domain.repository.AulaRepository;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.mapper.AulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;
    private final InstrutorRepository instrutorRepository;
    private final AulaMapper aulaMapper;

    public AulaService(AulaRepository aulaRepository, InstrutorRepository instrutorRepository, AulaMapper aulaMapper) {
        this.aulaRepository = aulaRepository;
        this.instrutorRepository = instrutorRepository;
        this.aulaMapper = aulaMapper;
    }

    @Transactional
    public AulaResponseDTO criarAula(AulaRequestDTO dto, String emailInstrutor) {

        Instrutor instrutor = instrutorRepository.findByUsuarioEmail(emailInstrutor)
                .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado com este e-mail"));

        Aula aula =  aulaMapper.toEntity(dto);

        aula.setInstrutor(instrutor);

        Aula aulaSalva = aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(aulaSalva);
    }
}