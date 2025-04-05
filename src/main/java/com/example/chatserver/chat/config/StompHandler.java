package com.example.chatserver.chat.config;

import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.repository.ChatRoomRepository;
import com.example.chatserver.chat.service.ChatService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {
    @Value("${jwt.secretKey}")
    private String secretKey;

    private final ChatService chatService;

    // subscribe, connect, disconnect, publish 요청이 들어오면 실행
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            log.info("connect 요청시 토큰 유효성 검증");
            String bearerToken = accessor.getFirstNativeHeader("Authorization");

            if (bearerToken == null) {
                log.error("Authorization 헤더가 없습니다.");
                throw new IllegalArgumentException("Authorization header is missing");
            }

            String token = bearerToken.substring(7);

            // 토큰 검증
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info("토큰 검증 완료");
        }

        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info("subscribe 요청시 토큰 유효성 검증");
            String bearerToken = accessor.getFirstNativeHeader("Authorization");

            if (bearerToken == null) {
                log.error("Authorization 헤더가 없습니다. (SUBSCRIBE)");
                throw new IllegalArgumentException("Authorization header is missing");
            }

            String token = bearerToken.substring(7); // Bearer 자르기

            // 검증
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            String roomId = accessor.getDestination().split("/")[2];
            // 현재 로그인 유저가 roomId에 존재하는지 여부.

            ChatRoom chatRoom = chatService.getChatRoomFetchJoin(Long.valueOf(roomId));

            boolean exists = chatRoom.hasParticipantsEmail(email);

            if (!exists) {
                throw new AuthenticationServiceException("해당 room에 권한이 없습니다.");
            }

            log.info("SUBSCRIBE 토큰 검증 완료");
        }

        return message;
    }
}
