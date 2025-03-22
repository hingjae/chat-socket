package com.example.chatserver.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // stomp
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOrigins("http://localhost:5173") // websocket cors 설정은 별도로 해준다.
                .withSockJS(); // ws:// 가 아닌 http:// 엔디포인트를 사용하는 설정.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 발행 prefix
        // ex. /publish/1 형태로 메시지 발행
        // /publish 로 시작하는 Url 패턴으로 메시지가 발행되면 @Controller 객체의 @MessageMapping 메서드로 라우팅.
        registry.setApplicationDestinationPrefixes("/publish");

        // 메시지 수신
        // ex. /topic/1 형태로 메시지 수신.
        registry.enableSimpleBroker("/topic");
    }
}
