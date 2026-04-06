package br.com.api.idrive.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record InstrutorRegistroDTO (
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "Formato de CPF inválido")
    String cpf,

    @NotBlank(message = "O E-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    String senha,

    @NotBlank(message = "O telefone é obrigatório")
    String telefone,

    @NotBlank(message = "O perfil é obrigatório")
    String tipoPerfil,

    @NotBlank(message = "A CNH é obrigatória")
    String cnh
){}
