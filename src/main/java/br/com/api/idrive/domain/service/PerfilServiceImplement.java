package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.perfil.PerfilPublicoResponseDTO;
import br.com.api.idrive.domain.model.TipoPerfil;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.AlunoRepository;
import br.com.api.idrive.domain.repository.AulaRepository;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.domain.model.StatusAula;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PerfilServiceImplement implements PerfilService {

    private final UsuarioRepository usuarioRepository;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final AulaRepository aulaRepository;

    public PerfilServiceImplement(UsuarioRepository usuarioRepository,
                         InstrutorRepository instrutorRepository,
                         AlunoRepository alunoRepository,
                         AulaRepository aulaRepository) {
        this.usuarioRepository   = usuarioRepository;
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository     = alunoRepository;
        this.aulaRepository      = aulaRepository;
    }

    public PerfilPublicoResponseDTO buscarMeuPerfil(String emailLogado) {
        Usuario usuario = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        return montarPerfil(usuario);
    }

    public PerfilPublicoResponseDTO buscarPorId(UUID id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado com o ID: " + id));
        return montarPerfil(usuario);
    }

    private PerfilPublicoResponseDTO montarPerfil(Usuario usuario) {
        boolean isInstrutor = TipoPerfil.INSTRUTOR.equals(usuario.getTipoPerfil());

        Double notaMedia  = null;
        String descricao  = null;
        int totalAulas    = 0;

        if (isInstrutor) {
            var instrutor = instrutorRepository.findByUsuarioEmail(usuario.getEmail())
                    .orElseThrow();
            notaMedia  = instrutor.getNotaMedia();
            descricao  = instrutor.getDescricao();
            totalAulas = aulaRepository
                    .findByInstrutorUsuarioEmail(usuario.getEmail())
                    .stream()
                    .filter(a -> StatusAula.CONCLUIDA.equals(a.getStatus()))
                    .toList()
                    .size();
        } else {
            var aluno = alunoRepository.findByUsuarioEmail(usuario.getEmail())
                    .orElseThrow();
            notaMedia  = aluno.getNotaMedia();
            totalAulas = aulaRepository
                    .findByAlunoUsuarioEmail(usuario.getEmail())
                    .stream()
                    .filter(a -> StatusAula.CONCLUIDA.equals(a.getStatus()))
                    .toList()
                    .size();
        }

        return new PerfilPublicoResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTipoPerfil().name(),
                notaMedia,
                descricao,
                totalAulas
        );
    }
}