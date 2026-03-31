package br.com.api.idrive.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@lombok
@Getter
@Setter
@MappedSuperclass // não cria uma tabela pessoa mas coloca os campos dele nas outras classes aluno e instrutor
@NoArgsConstructor // construtor vazio
@AllArgsConstructor // construtor completo
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 150)
    String nome;

    @Column(nullable = false, length = 11, unique = true)
    String cpf;

    @Column(nullable = false, length = 100, unique = true)
    String email;

    @Column(nullable = false)
    String senha;

    @Column(nullable = false, length = 15, unique = true)
    String telefone;

}
