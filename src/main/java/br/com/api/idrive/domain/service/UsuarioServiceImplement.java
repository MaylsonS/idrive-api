package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaRequestDTO;
import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioRegistroDTO;
import br.com.api.idrive.domain.dto.usuario.UsuarioResponseDTO;
import br.com.api.idrive.domain.model.Aluno;
import br.com.api.idrive.domain.model.Usuario;
import br.com.api.idrive.domain.repository.AlunoRepository;
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
    private final AlunoRepository alunoRepository;

    public UsuarioServiceImplement(UsuarioRepository repository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder,  AlunoRepository alunoRepository) {
        this.userRepository = repository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.alunoRepository = alunoRepository;
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

        Usuario usuarioSalvo = userRepository.save(usuario);

        if ("ALUNO".equals(usuarioSalvo.getTipoPerfil().name())) {
            Aluno aluno = new Aluno();
            aluno.setUsuario(usuarioSalvo);
            alunoRepository.save(aluno);
        }

        return usuarioSalvo;
    }

}