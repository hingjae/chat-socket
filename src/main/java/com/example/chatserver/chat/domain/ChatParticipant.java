package com.example.chatserver.chat.domain;

import com.example.chatserver.common.domain.BaseTimeEntity;
import com.example.chatserver.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ChatParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public ChatParticipant(Long id, ChatRoom chatRoom, Member member) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.member = member;
    }

    public static ChatParticipant of(ChatRoom chatRoom, Member member) {
        return ChatParticipant.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();
    }
}
