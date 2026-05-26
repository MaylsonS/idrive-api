package br.com.api.idrive.controller;

import br.com.api.idrive.domain.model.ChatMessage;
import br.com.api.idrive.domain.model.MessageEntity;
import br.com.api.idrive.domain.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;


    @MessageMapping("/chat/{roomId}/send")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessage handleMessage(@DestinationVariable String roomId, ChatMessage message) {

        message.setTimestamp(LocalDateTime.now());
        message.setRoomId(roomId);

        MessageEntity entity = new MessageEntity();
        entity.setRoomId(roomId);
        entity.setSender(message.getSender());
        entity.setContent(message.getContent());
        entity.setTimestamp(message.getTimestamp());

        messageRepository.save(entity);

        return message;

    }
}
