//package com.example.chatserver.chat.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.*;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Component
//public class SimpleWebSocketHandler extends TextWebSocketHandler {
//
//    // 동시에 연결 요청이 왔을 때 thread-safe 해야함.
//    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
//
//    /**
//     * client 메모리에 등록
//     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session);
//        log.info("session connected: {}", session.getId());
//    }
//
//    /**
//     * 사용자에게 메시지 전송
//     */
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("receive message: {}", payload);
//        for (WebSocketSession targetSession : sessions) {
//            if (targetSession.isOpen()) {
//                targetSession.sendMessage(new TextMessage(payload));
//            }
//        }
//    }
//
//    /**
//     * 연결이 끊기면 client를 메모리에서 삭제
//     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
//        log.info("session disconnected: {}", session.getId());
//    }
//}
