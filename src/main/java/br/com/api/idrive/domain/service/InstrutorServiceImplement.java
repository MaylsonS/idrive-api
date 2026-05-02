package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.Aula.AulaResponseDTO;
import br.com.api.idrive.domain.dto.Instrutor.InstrutorRegistroDTO;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.InstrutorMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstrutorServiceImplement implements InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstrutorMapper instrutorMapper;
    private final PasswordEncoder passwordEncoder;

    public InstrutorServiceImplement(InstrutorRepository instrutorRepository, UsuarioRepository usuarioRepository, InstrutorMapper instrutorMapper, PasswordEncoder passwordEncoder) {
        this.instrutorRepository = instrutorRepository;
        this.usuarioRepository = usuarioRepository;
        this.instrutorMapper = instrutorMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Instrutor registrar(InstrutorRegistroDTO dto) {
        if(usuarioRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("Este CPF já está em uso.");
        }
        if(usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Este e-mail já está em uso.");
        }

        Instrutor instrutor = instrutorMapper.toEntity(dto);
        instrutor.getUsuario().setSenha(passwordEncoder.encode(dto.senha()));

        return instrutorRepository.save(instrutor);
    }

    @Override
    public List<AulaResponseDTO> listarAulas() {
        return List.of();
    }

}