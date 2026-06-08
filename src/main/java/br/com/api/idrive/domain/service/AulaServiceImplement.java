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

import java.time.LocalDateTime;
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

        if (dto.inicio().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível criar um anúncio com data anterior a hoje.");
        }
        if (dto.fim().isBefore(dto.inicio())) {
            throw new IllegalArgumentException("O horário de término deve ser após o horário de início.");
        }

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

        LocalDateTime agora = LocalDateTime.now();

        List<Aula> anuncios;

        if ("ALUNO".equals(usuario.getTipoPerfil().name())) {
            anuncios = aulaRepository
                    .findByInstrutorIsNotNullAndStatusAndInicioAfter(StatusAula.ABERTA, agora);
        } else {
            anuncios = aulaRepository
                    .findByAlunoIsNotNullAndStatusAndInicioAfter(StatusAula.ABERTA, agora);
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

        if (!StatusAula.ABERTA.equals(aula.getStatus())) {
            throw new IllegalStateException("Só é possível editar anúncios ABERTOS.");
        }

        aula.setInicio(dto.inicio());
        aula.setFim(dto.fim());
        aula.setValor(dto.valor());
        aula.setDescricao(dto.descricao());
        Aula aulaAtualizada = aulaRepository.save(aula);
        return this.toResponseDTO(aulaAtualizada);
    }

    @Transactional
    public void excluirAnuncio(UUID id, String emailLogado) {
        Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Anúncio de aula não encontrado com o ID fornecido."));
        validarDonoDoAnuncio(aula, emailLogado);

        if (!StatusAula.ABERTA.equals(aula.getStatus())) {
            throw new IllegalStateException("Só é possível excluir anúncios ABERTOS. Caso necessário, cancele a aula.");
        }
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

    @Transactional
    public void cancelarAnunciosVencidos() {
        List<Aula> vencidas = aulaRepository
                .findByStatusAndInicioAfterIsFalse(StatusAula.ABERTA, LocalDateTime.now());

        vencidas.forEach(aula -> aula.setStatus(StatusAula.CANCELADA));

        aulaRepository.saveAll(vencidas);
    }

    @Override
    public List<AulaResponseDTO> listarAulasPorInstrutor(UUID instrutorId) {
        instrutorRepository.findById(instrutorId)
                .orElseThrow(() -> new IllegalArgumentException("Instrutor não encontrado."));

        return aulaRepository
                .findByInstrutorIdAndStatusIn(
                        instrutorId,
                        List.of(StatusAula.ABERTA, StatusAula.ACEITA)
                )
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

}