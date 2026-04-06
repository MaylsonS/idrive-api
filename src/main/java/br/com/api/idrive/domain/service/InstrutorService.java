package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.InstrutorResponseDTO;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.model.TipoPerfil;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final UsuarioRepository usuarioRepository;

    public InstrutorService(InstrutorRepository instrutorRepository, UsuarioRepository usuarioRepository) {
        this.instrutorRepository = instrutorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Instrutor registrar(InstrutorRegistroDTO dto) {
        if(usuarioRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está em uso.");
        }
        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setTelefone(dto.telefone());
        usuario.setTipoPerfil(TipoPerfil.valueOf(dto.tipoPerfil()));

        Instrutor instrutor = new Instrutor();
        instrutor.setCnh(dto.cnh());
        instrutor.setUsuario(usuario);

        return instrutorRepository.save(instrutor);
    }

    public List<InstrutorResponseDTO> listarTodos() {
        return instrutorRepository.findAll()
                .stream()
                .map(instrutor -> new InstrutorResponseDTO(
                        instrutor.getId(),
                        instrutor.getUsuario().getNome(),
                        instrutor.getUsuario().getEmail(),
                        instrutor.getCnh(),
                        instrutor.getUsuario().getTipoPerfil().name()
                )).toList();
    }
}