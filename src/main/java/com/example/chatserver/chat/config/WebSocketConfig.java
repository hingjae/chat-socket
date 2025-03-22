//package com.example.chatserver.chat.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    private final SimpleWebSocketHandler simpleWebSocketHandler;
//
//    // 웹소켓을 처리할 핸들러를 등록해
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // /connect url 로 websocket 연결 요청이 들어오면, handler 클래스가 처리함.
//        registry.addHandler(simpleWebSocketHandler, "/connect")
//                .setAllowedOrigins("http://localhost:5173"); // websocket 프로토콜용 cors 설정.
//
//    }
//}
