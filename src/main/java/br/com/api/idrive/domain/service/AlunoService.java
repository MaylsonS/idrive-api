package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.AlunoRegistroDTO;
import br.com.api.idrive.domain.model.Aluno;
import br.com.api.idrive.domain.repository.AlunoRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.AlunoMapper;
import br.com.api.idrive.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoMapper mapper;
    private final AlunoRepository repository;

    public AlunoService(UsuarioRepository usuarioRepositorio, AlunoMapper mapper, AlunoRepository repository) {
        this.usuarioRepository = usuarioRepositorio;
        this.mapper = mapper;
        this.repository = repository;
    }

    public Aluno registrar(AlunoRegistroDTO dto) {
        if(usuarioRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está em uso.");
        }
        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        Aluno aluno = mapper.toEntity(dto);
        return repository.save(aluno);
    }
}
