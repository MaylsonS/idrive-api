package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "instrutores")
public class Instrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, unique = true, length = 11)
    private String cnh;

    @ManyToMany(mappedBy = "instrutores")
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "instrutor")
    private List<Aula> aulas = new ArrayList<>();
}