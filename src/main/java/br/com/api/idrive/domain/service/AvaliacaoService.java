package br.com.api.idrive.domain.service;

import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoAlunoResponseDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorRequestDTO;
import br.com.api.idrive.domain.dto.avaliacao.AvaliacaoInstrutorResponseDTO;
import br.com.api.idrive.domain.model.*;
import br.com.api.idrive.domain.repository.*;
import br.com.api.idrive.mapper.AvaliacaoAlunoMapper;
import br.com.api.idrive.mapper.AvaliacaoInstrutorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    private final AvaliacaoInstrutorRepository avaliacaoInstrutorRepository;
    private final AvaliacaoAlunoRepository avaliacaoAlunoRepository;
    private final AulaRepository aulaRepository;
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final AvaliacaoAlunoMapper avaliacaoAlunoMapper;
    private final AvaliacaoInstrutorMapper avaliacaoInstrutorMapper;

    public AvaliacaoService(AvaliacaoInstrutorRepository avaliacaoInstrutorRepository, AvaliacaoAlunoRepository avaliacaoAlunoRepository, AulaRepository aulaRepository, InstrutorRepository instrutorRepository, AlunoRepository alunoRepository, AvaliacaoInstrutorMapper avaliacaoInstrutorMapper, AvaliacaoAlunoMapper avaliacaoAlunoMapper) {

        this.avaliacaoInstrutorRepository = avaliacaoInstrutorRepository;
        this.avaliacaoAlunoRepository = avaliacaoAlunoRepository;
        this.aulaRepository = aulaRepository;
        this.instrutorRepository = instrutorRepository;
        this.alunoRepository = alunoRepository;
        this.avaliacaoAlunoMapper = avaliacaoAlunoMapper;
        this.avaliacaoInstrutorMapper = avaliacaoInstrutorMapper;
    }

    @Transactional
    public AvaliacaoInstrutorResponseDTO avaliarInstrutor(AvaliacaoInstrutorRequestDTO dto, String emailLogado) {

        Aula aula = aulaRepository.findById(dto.aulaId())
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada."));

        if (!StatusAula.CONCLUIDA.equals(aula.getStatus())) {
            throw new IllegalStateException("Só é possível avaliar aulas com status CONCLUIDA.");
        }

        if (aula.getAluno() == null || !aula.getAluno().getUsuario().getEmail().equals(emailLogado)) {
            throw new IllegalStateException("Você não tem permissão para avaliar esta aula.");
        }

        if (avaliacaoInstrutorRepository.existsByAulaId(dto.aulaId())) {
            throw new IllegalStateException("Esta aula já foi avaliada.");
        }

        double media = (dto.pontualidade() + dto.seguirRegrasDeTransito() + dto.clareza()) / 3.0;

        AvaliacaoInstrutor avaliacao = new AvaliacaoInstrutor();
        avaliacao.setAula(aula);
        avaliacao.setInstrutor(aula.getInstrutor());
        avaliacao.setPontualidade(dto.pontualidade());
        avaliacao.setSeguirRegrasDeTransito(dto.seguirRegrasDeTransito());
        avaliacao.setClareza(dto.clareza());
        avaliacao.setMedia(media);

        avaliacaoInstrutorRepository.save(avaliacao);

        Double novaMedia = avaliacaoInstrutorRepository.calcularMediaPorInstrutor(aula.getInstrutor().getId());
        aula.getInstrutor().setNotaMedia(novaMedia);
        instrutorRepository.save(aula.getInstrutor());

        return avaliacaoInstrutorMapper.toResponseDTO(avaliacao);


    }

    @Transactional
    public AvaliacaoAlunoResponseDTO avaliarAluno(AvaliacaoAlunoRequestDTO dto, String emailLogado) {

        Aula aula = aulaRepository.findById(dto.aulaId())
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada."));

        if (!StatusAula.CONCLUIDA.equals(aula.getStatus())) {
            throw new IllegalStateException("Só é possível avaliar aulas com status CONCLUIDA.");
        }

        if (aula.getInstrutor() == null || !aula.getInstrutor().getUsuario().getEmail().equals(emailLogado)) {
            throw new IllegalStateException("Você não tem permissão para avaliar esta aula.");
        }

        if (avaliacaoAlunoRepository.existsByAulaId(dto.aulaId())) {
            throw new IllegalStateException("Esta aula já foi avaliada.");
        }

        double media = (dto.pontualidade() + dto.receptividade()) / 2.0;

        AvaliacaoAluno avaliacao = new AvaliacaoAluno();
        avaliacao.setAula(aula);
        avaliacao.setAluno(aula.getAluno());
        avaliacao.setPontualidade(dto.pontualidade());
        avaliacao.setReceptividade(dto.receptividade());
        avaliacao.setMedia(media);

        avaliacaoAlunoRepository.save(avaliacao);

        Double novaMedia = avaliacaoAlunoRepository.calcularMediaPorAluno(aula.getAluno().getId());
        aula.getAluno().setNotaMedia(novaMedia);
        alunoRepository.save(aula.getAluno());

        return avaliacaoAlunoMapper.toResponseDTO(avaliacao);

    }
}