package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.model.Aula;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.repository.AulaRepository;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.mapper.AulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AulaServiceImplement implements AulaService {

    private final AulaRepository aulaRepository;
    private final InstrutorRepository instrutorRepository;
    private final AulaMapper aulaMapper;

    public AulaServiceImplement(AulaRepository aulaRepository, InstrutorRepository instrutorRepository, AulaMapper aulaMapper) {
        this.aulaRepository = aulaRepository;
        this.instrutorRepository = instrutorRepository;
        this.aulaMapper = aulaMapper;
    }

    @Transactional
    public AulaResponseDTO anuncioAula(AulaRequestDTO dto, String emailInstrutor) {

        Instrutor instrutor = instrutorRepository.findByUsuarioEmail(emailInstrutor)
                .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado com este e-mail"));

        Aula aula =  aulaMapper.toEntity(dto);

        aula.setInstrutor(instrutor);

        Aula aulaSalva = aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(aulaSalva);
    }
}