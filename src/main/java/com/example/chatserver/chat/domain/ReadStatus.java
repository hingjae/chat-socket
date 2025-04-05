package com.example.chatserver.chat.domain;

import com.example.chatserver.common.domain.BaseTimeEntity;
import com.example.chatserver.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class ReadStatus extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id", nullable = false)
    private ChatMessage chatMessage;

    private Boolean isRead;

    @Builder
    public ReadStatus(Long id, ChatRoom chatRoom, Member member, ChatMessage chatMessage, Boolean isRead) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.member = member;
        this.chatMessage = chatMessage;
        this.isRead = isRead;
    }

    public static ReadStatus of(ChatRoom chatRoom, ChatMessage chatMessage, ChatParticipant chatParticipant, Member memberSender) {
        Member participantMember = chatParticipant.getMember();
        return ReadStatus.builder()
                .chatRoom(chatRoom)
                .member(participantMember)
                .chatMessage(chatMessage)
                .isRead(Objects.equals(participantMember, memberSender))
                .build();
    }

    public void updateIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
