package com.example.chatserver.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// 스프링과 Stomp 는 세션관리를 자동으로 해준다. -> 프론트에서 connect를 남발해서도 안된다.
// connect/disconnect event를 기록함. 연결된 세션수 실시간 확인 목적 , 로그 디버깅 목적
@Component
@Slf4j
public class StompEventListener {

    private final Set<String> sessions = ConcurrentHashMap.newKeySet(); // 디버깅용.

    // 웹소켓 세션이 연결될 때 실행되는 메서드를 정의
    @EventListener
    public void connectHandler(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.add(accessor.getSessionId());
        log.info("connect session Id : {}", accessor.getSessionId());
        log.info("total session : {}", sessions.size());
    }

    // 웹소켓 세션이 끊길 때 실행되는 메서드를 정의
    @EventListener
    public void disconnectHandler(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        sessions.remove(accessor.getSessionId());
        log.info("disconnect session Id : {}", accessor.getSessionId());
        log.info("total session : {}", sessions.size());
    }
}
