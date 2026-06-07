package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.model.*;
import br.com.api.idrive.domain.repository.AlunoRepository;
import br.com.api.idrive.domain.repository.AulaRepository;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.AulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
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

    private AulaResponseDTO toResponseDTO(Aula aula) {
        UUID autorId    = null;
        UUID coAutorId  = null;
        String coAutorNome = aula.getCoAutor();

        if (aula.getInstrutor() != null && aula.getAluno() != null) {
            boolean instrutorCriou = aula.getAutor()
                    .equals(aula.getInstrutor().getUsuario().getNome());
            if (instrutorCriou) {
                autorId    = aula.getInstrutor().getId();
                coAutorId  = aula.getAluno().getId();
                coAutorNome = aula.getAluno().getUsuario().getNome();
            } else {
                autorId    = aula.getAluno().getId();
                coAutorId  = aula.getInstrutor().getId();
                coAutorNome = aula.getInstrutor().getUsuario().getNome();
            }
        } else if (aula.getInstrutor() != null) {
            autorId = aula.getInstrutor().getId();
        } else if (aula.getAluno() != null) {
            autorId = aula.getAluno().getId();
        }

        return new AulaResponseDTO(
                aula.getId(),
                aula.getInicio(),
                aula.getFim(),
                aula.getValor(),
                aula.getDescricao(),
                aula.getAutor(),
                autorId,
                coAutorNome,
                coAutorId,
                aula.getStatus()
        );
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
            aula.setAutor(instrutor.getUsuario().getNome());
            aula.setStatus(StatusAula.ABERTA);

        } else if ("ALUNO".equals(usuario.getTipoPerfil().name())) {
            Aluno aluno = alunoRepository.findByUsuarioEmail(emailLogado)
                    .orElseThrow(() -> new IllegalArgumentException("Cadastro de Aluno não encontrado."));
            aula.setAluno(aluno);
            aula.setAutor(aluno.getUsuario().getNome());
            aula.setStatus(StatusAula.ABERTA);
        }

        return toResponseDTO(aulaRepository.save(aula));

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
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AulaResponseDTO> listarAnunciosPublicos(String emailLogado) {

        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        List<Aula> anuncios;

        if ("ALUNO".equals(usuario.getTipoPerfil().name())) {
            anuncios = aulaRepository.findByInstrutorIsNotNullAndStatus(StatusAula.ABERTA);
        } else {
            anuncios = aulaRepository.findByAlunoIsNotNullAndStatus(StatusAula.ABERTA);
        }

        return anuncios.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public AulaResponseDTO editarAnuncio(UUID id, AulaRequestDTO dto, String emailLogado) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Anúncio de aula não encontrado com o ID fornecido."));

        validarDonoDoAnuncio(aula, emailLogado);
        aula.setInicio(dto.inicio());
        aula.setFim(dto.fim());
        aula.setValor(dto.valor());
        aula.setDescricao(dto.descricao());
        Aula aulaAtualizada = aulaRepository.save(aula);
        return aulaMapper.toResponseDTO(aulaAtualizada);
    }

    @Transactional
    public void excluirAnuncio(UUID id, String emailLogado) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Anúncio de aula não encontrado com o ID fornecido."));
        validarDonoDoAnuncio(aula, emailLogado);
        aulaRepository.delete(aula);
    }

    private void validarDonoDoAnuncio(Aula aula, String emailLogado) {
        String emailDonoAnuncio = null;

        if (aula.getInstrutor() != null) {
            emailDonoAnuncio = aula.getInstrutor().getUsuario().getEmail();
        } else if (aula.getAluno() != null) {
            emailDonoAnuncio = aula.getAluno().getUsuario().getEmail();
        }

        if (emailDonoAnuncio == null || !emailDonoAnuncio.equals(emailLogado)) {
            throw new SecurityException("Acesso negado: Você não tem permissão para alterar ou excluir este anúncio.");
        }
    }

}