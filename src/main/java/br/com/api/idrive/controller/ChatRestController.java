package br.com.api.idrive.controller;

import br.com.api.idrive.domain.model.MessageEntity;
import br.com.api.idrive.domain.repository.MessageRepository;
import br.com.api.idrive.domain.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/chat")
@CrossOrigin(origins = "*")
public class ChatRestController {

    private final MessageRepository repository;
    private final UsuarioRepository usuarioRepository;

    public ChatRestController(MessageRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<MessageEntity>> getChatHistory(@PathVariable String roomId) {
        List<MessageEntity> history = repository.findByRoomIdOrderByTimestampAsc(roomId);
        return ResponseEntity.ok(history);
    }

    // NOVO: retorna o nome do contato para uma sala específica.
    // Usado pelo front quando a sala ainda não tem mensagens (estado não passa pelo /rooms).
    @GetMapping("/{roomId}/info")
    public ResponseEntity<Map<String, Object>> getRoomInfo(
            @PathVariable String roomId,
            Authentication authentication) {

        String emailLogado = authentication.getName();
        var usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String userId = usuarioLogado.getId().toString();

        String[] partes = roomId.split("_");
        String outroId = Arrays.stream(partes)
                .filter(p -> !p.equals(userId))
                .findFirst()
                .orElse("");

        String nomeOutro = usuarioRepository.findById(UUID.fromString(outroId))
                .map(u -> u.getNome())
                .orElse("Usuário");

        return ResponseEntity.ok(Map.of("name", nomeOutro));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Map<String, Object>>> getRooms(Authentication authentication) {
        String emailLogado = authentication.getName();

        var usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String userId = usuarioLogado.getId().toString();

        List<MessageEntity> todasMensagens = repository.findAll();

        Map<String, List<MessageEntity>> porSala = todasMensagens.stream()
                .filter(m -> m.getRoomId().contains(userId))
                .collect(Collectors.groupingBy(MessageEntity::getRoomId));

        List<Map<String, Object>> rooms = new ArrayList<>();

        for (Map.Entry<String, List<MessageEntity>> entry : porSala.entrySet()) {
            String roomId = entry.getKey();
            List<MessageEntity> msgs = entry.getValue();

            MessageEntity ultima = msgs.stream()
                    .max(Comparator.comparing(MessageEntity::getTimestamp))
                    .orElse(null);

            String[] partes = roomId.split("_");
            String outroId = Arrays.stream(partes)
                    .filter(p -> !p.equals(userId))
                    .findFirst()
                    .orElse("");

            String nomeOutro = usuarioRepository.findById(UUID.fromString(outroId))
                    .map(u -> u.getNome())
                    .orElse("Usuário");

            Map<String, Object> room = new HashMap<>();
            room.put("id", roomId);
            room.put("name", nomeOutro);
            room.put("lastMessage", ultima != null ? ultima.getContent() : "");
            room.put("time", ultima != null
                    ? ultima.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm"))
                    : "");
            room.put("online", false);

            rooms.add(room);
        }

        rooms.sort((a, b) -> ((String) b.get("time")).compareTo((String) a.get("time")));
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/rooms/iniciar")
    public ResponseEntity<Map<String, String>> iniciarConversa(
            @RequestParam UUID outroUsuarioId,
            Authentication authentication) {

        String emailLogado = authentication.getName();
        var usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String idA = usuarioLogado.getId().toString();
        String idB = outroUsuarioId.toString();

        String roomId = idA.compareTo(idB) < 0
                ? idA + "_" + idB
                : idB + "_" + idA;

        return ResponseEntity.ok(Map.of("roomId", roomId));
    }
}