package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.InstrutorResponseDTO;
import br.com.api.idrive.domain.service.InstrutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService service){
        this.instrutorService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody @Valid InstrutorRegistroDTO dto) {
        instrutorService.registrar(dto);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/todos")
    public ResponseEntity<List<InstrutorResponseDTO>> listar() {
        List<InstrutorResponseDTO> users = instrutorService.listarTodos();
        return ResponseEntity.ok(users);
    }
}
