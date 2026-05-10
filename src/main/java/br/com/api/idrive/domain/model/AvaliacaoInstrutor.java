package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "avaliacoes_instrutor")
public class AvaliacaoInstrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "aula_id", nullable = false, unique = true)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Instrutor instrutor;

    @Column(nullable = false)
    private Integer pontualidade;

    @Column(nullable = false)
    private Integer seguirRegrasDeTransito;

    @Column(nullable = false)
    private Integer clareza;

    @Column(nullable = false)
    private Double media;
}