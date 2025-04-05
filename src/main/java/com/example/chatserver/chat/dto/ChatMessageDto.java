package com.example.chatserver.chat.dto;

import com.example.chatserver.chat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private Long roomId;

    private String sender;

    private String content;

    public ChatMessageDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getChatRoom().getId();
        this.sender = chatMessage.getMember().getEmail();
        this.content = chatMessage.getContent();
    }
}
