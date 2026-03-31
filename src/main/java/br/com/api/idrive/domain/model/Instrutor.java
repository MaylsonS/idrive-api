package br.com.api.idrive.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@lombok
@Getter @Setter
@NoArgsConstructor
@Entity
public class Instrutor extends Pessoa{

    @Column(unique = true, nullable = false, length = 150)
    String formacao;

    @Column(unique = true, nullable = false, length = 50)
    String titulacao;

    @ManyToMany(mappedBy = "instrutor")
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "instrutor")
    private List<Aula> aulas = new ArrayList<>();



}
