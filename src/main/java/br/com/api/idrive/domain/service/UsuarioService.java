package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository userRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper usuarioMapper) {
        this.userRepository = repository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public Usuario registrar(UsuarioRegistroDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }
        if (userRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está cadastrado.");
        }

        Usuario usuario = usuarioMapper.toEntity(dto);

        return userRepository.save(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return userRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }
}