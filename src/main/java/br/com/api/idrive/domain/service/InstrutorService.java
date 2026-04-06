package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.InstrutorResponseDTO;
import br.com.api.idrive.domain.model.Instrutor;
import br.com.api.idrive.domain.repository.InstrutorRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import br.com.api.idrive.mapper.InstrutorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstrutorMapper instrutorMapper;

    public InstrutorService(InstrutorRepository instrutorRepository, UsuarioRepository usuarioRepository, InstrutorMapper instrutorMapper) {
        this.instrutorRepository = instrutorRepository;
        this.usuarioRepository = usuarioRepository;
        this.instrutorMapper = instrutorMapper;
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

        return instrutorRepository.save(instrutor);
    }

    public List<InstrutorResponseDTO> listarTodos() {
        return instrutorRepository.findAll()
                .stream()
                .map(instrutorMapper::toResponseDTO)
                .toList();
    }
}