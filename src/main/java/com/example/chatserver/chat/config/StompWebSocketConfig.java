package com.example.chatserver.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker // stomp
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOrigins("http://localhost:5173") // websocket cors 설정은 별도로 해준다.
                .withSockJS(); // ws:// 가 아닌 http:// 엔디포인트를 사용하는 설정.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 발행 prefix
        // ex. client 가 /publish/1 경로로 메시지를 발행
        // @Controller 객체의 @MessageMapping 메서드로 라우팅.
        registry.setApplicationDestinationPrefixes("/publish");

        // 메시지 수신
        // stomp 내부 브로커를 사용해도 되지만 rabbitMQ, kafka, Redis pub/sub 같은 외부 브로커를 사용할 수도 있다.
        // ex. /topic/1 형태로 메시지 수신.
        registry.enableSimpleBroker("/topic");
    }

    // WebSocket(connect, subscribe, disconnect)등의 요청시에는 http header 를 넣어 토큰을 검증가능.
    // 하지만 ws 프로토콜은 http 기반이 아님. 즉 헤더검증을 못함.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
