package br.com.api.idrive.controller;

import br.com.api.idrive.domain.service.UsuarioServiceImplement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServiceImplement userService;

    public UsuarioController(UsuarioServiceImplement service) {
        this.userService = service;
    }


}