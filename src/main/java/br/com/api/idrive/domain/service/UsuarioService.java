package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.model.TipoPerfil;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;

    public UsuarioService(UsuarioRepository repository) {
        this.userRepository = repository;
    }

    @Transactional
    public Usuario registrar(UsuarioRegistroDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }
        if (userRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha()); // Futuramente mudar para senha criptografada
        usuario.setTelefone(dto.telefone());
        usuario.setTipoPerfil(TipoPerfil.valueOf(dto.tipoPerfil()));

        return userRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getTipoPerfil().name()
                ))
                .toList();
    }
}