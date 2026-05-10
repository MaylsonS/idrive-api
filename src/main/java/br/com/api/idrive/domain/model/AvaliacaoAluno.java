package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "avaliacoes_aluno")
public class AvaliacaoAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "aula_id", nullable = false, unique = true)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    // Criterios de avaliacao do aluno (1 a 5)
    @Column(nullable = false)
    private Integer pontualidade;

    @Column(nullable = false)
    private Integer receptividade;

    // Media calculada dos dois criterio
    @Column(nullable = false)
    private Double media;
}