package br.com.api.idrive.controller;

import br.com.api.idrive.domain.dto.Instrutor.InstrutorRegistroDTO;
import br.com.api.idrive.domain.dto.Instrutor.InstrutorResponseDTO;
import br.com.api.idrive.domain.service.InstrutorServiceImplement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutores")
public class InstrutorController {

    @Autowired
    private InstrutorServiceImplement instrutorService;

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
