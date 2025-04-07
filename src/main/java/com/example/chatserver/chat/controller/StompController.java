package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessageDto;
import com.example.chatserver.chat.service.ChatService;
import com.example.chatserver.chat.service.RedisPubSubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ChatService chatService;
    private final SimpMessageSendingOperations messageTemplate;
    private final RedisPubSubService redisPubSubService;

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
    // 클라이언트에서 /publish/{roomId} 로 publish 했을 때 받는 부분.
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, @Payload ChatMessageDto chatMessageDto) throws JsonProcessingException {
        log.info("message :{}", chatMessageDto);
        chatService.saveMessage(roomId, chatMessageDto);

        // /topic/{roomId} 로 메시지 전달. /topic/{roomId}를 subscribe하는 클라이언트들은 메시지 수신
        // messageTemplate.convertAndSend("/topic/" + roomId, message); //

        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(chatMessageDto);

        // Redis로 메시지 publish
        redisPubSubService.publish("chat", message);
    }
}
