package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImplement implements UsuarioService{

    private final UsuarioRepository userRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImplement(UsuarioRepository repository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Usuario registar(UsuarioRegistroDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }
        if (userRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return userRepository.save(usuario);
    }

}