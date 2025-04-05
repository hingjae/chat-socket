package com.example.chatserver.chat.dto;

import com.example.chatserver.chat.domain.ChatMessage;
import lombok.Data;

import java.util.List;

@Data
public class ChatMessageDtoList {
    private List<ChatMessageDto> chatMessages;

    public ChatMessageDtoList (List<ChatMessageDto> chatMessageDtos) {
        this.chatMessages = chatMessageDtos;
    }

    public static ChatMessageDtoList from(List<ChatMessage> chatMessages) {
        List<ChatMessageDto> chatMessageDtos = chatMessages.stream()
                .map(ChatMessageDto::new)
                .toList();

        return new ChatMessageDtoList(chatMessageDtos);
    }
}
