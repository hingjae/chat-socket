package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class StompController {

    // DestinationVariable : @MessageMapping 어노테이션으로 정의된 WebSocket Controller 내애서만 사용.
    // 해당 roomId에 메시지를 발행하여 특정 topic을 구독한 클라이언트에 전달.
    @MessageMapping("/publish/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, @Payload ChatMessage message) {
        log.info("roomId : {}, message : {}", roomId, message);

        return message;
    }
}
