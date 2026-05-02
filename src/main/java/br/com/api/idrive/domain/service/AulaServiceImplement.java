package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.model.Aluno;
import br.com.api.idrive.domain.model.Aula;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.AlunoRepository;
import br.com.api.idrive.domain.repository.AulaRepository;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.AulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AulaServiceImplement implements AulaService {

    private final AulaRepository aulaRepository;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AulaMapper aulaMapper;

    public AulaServiceImplement(AulaRepository aulaRepository, InstrutorRepository instrutorRepository, AlunoRepository alunoRepository, UsuarioRepository usuarioRepository, AulaMapper aulaMapper) {
        this.aulaRepository = aulaRepository;
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository = alunoRepository;
        this.usuarioRepository = usuarioRepository;
        this.aulaMapper = aulaMapper;
    }

    @Transactional
    @Override
    public AulaResponseDTO anuncioAula(AulaRequestDTO dto, String emailLogado) {

        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com este e-mail"));

        Aula aula = aulaMapper.toEntity(dto);

        if ("INSTRUTOR".equals(usuario.getTipoPerfil().name())) {
            Instrutor instrutor = instrutorRepository.findByUsuarioEmail(emailLogado)
                    .orElseThrow(() -> new IllegalArgumentException("Cadastro de Instrutor não encontrado."));
            aula.setInstrutor(instrutor);

        } else if ("ALUNO".equals(usuario.getTipoPerfil().name())) {
            Aluno aluno = alunoRepository.findByUsuarioEmail(emailLogado)
                    .orElseThrow(() -> new IllegalArgumentException("Cadastro de Aluno não encontrado."));
            aula.setAluno(aluno);
        }

        Aula aulaSalva = aulaRepository.save(aula);

        return aulaMapper.toResponseDTO(aulaSalva);
    }

    @Override
    public List<AulaResponseDTO> listarAulas(String emailLogado) {

        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        List<Aula> minhasAulas;

        if ("INSTRUTOR".equals(usuario.getTipoPerfil().name())) {
            minhasAulas = aulaRepository.findByInstrutorUsuarioEmail(emailLogado);
        } else {
            minhasAulas = aulaRepository.findByAlunoUsuarioEmail(emailLogado);
        }

        return minhasAulas.stream()
                .map(aulaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}