package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 25)
    LocalDateTime inicio;

    @Column(nullable = false,length = 25)
    LocalDateTime fim;

    @Column(unique = true, nullable = false,length = 15)
    Double valor;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name="instrutor_id")
    private Instrutor instrutor;

}



