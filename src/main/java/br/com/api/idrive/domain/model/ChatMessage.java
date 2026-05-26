package br.com.api.idrive.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ChatMessage {
    private String sender;
    private String content;
    private String roomId;
    private LocalDateTime timestamp;

}
