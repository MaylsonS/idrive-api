package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@Entity
@Table(name="Alunos")
@NoArgsConstructor
public class Aluno extends Pessoa{

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aluno_instrutor",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "instrutor_id") )
    private List<Instrutor> instrutor = new ArrayList<>();

    @OneToMany(mappedBy = "aulas")
    private List<Aula> aulas = new ArrayList<>();


}
