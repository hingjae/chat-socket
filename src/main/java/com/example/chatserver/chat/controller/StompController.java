package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompController {

    private final SimpMessageSendingOperations messageTemplate;

    // DestinationVariable : @MessageMapping 어노테이션으로 정의된 WebSocket Controller 내애서만 사용.
    // 해당 roomId에 메시지를 발행하여 특정 topic을 구독한 클라이언트에 전달.
//    @MessageMapping("/{roomId}")
//    @SendTo("/topic/{roomId}")
//    public ChatMessage sendMessage(@DestinationVariable Long roomId, @Payload ChatMessage message) {
//        log.info("roomId : {}, message : {}", roomId, message);
//
//        return message;
//    }

    //방법2. MessageMapping 어노테이션만 사용
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessage message) {
        log.info("message :{}", message);
        messageTemplate.convertAndSend("/topic/" + roomId, message);
    }
}
