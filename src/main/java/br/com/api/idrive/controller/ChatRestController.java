package br.com.api.idrive.controller;

import br.com.api.idrive.domain.model.MessageEntity;
import br.com.api.idrive.domain.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chat")
@CrossOrigin(origins = "*")
public class ChatRestController {

    @Autowired
    private MessageRepository repository;

    @GetMapping("/{roomId/messages")
    public ResponseEntity<List<MessageEntity>> getChatHistory(@PathVariable String roomId) {
        List<MessageEntity> history = repository.findByRoomIdOrderByTimestampAsc(roomId);
        return ResponseEntity.ok(history);
    }


}
