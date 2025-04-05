package com.example.chatserver.chat.domain;

import com.example.chatserver.common.domain.BaseTimeEntity;
import com.example.chatserver.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isGroupChat;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatParticipant> chatParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Builder
    public ChatRoom(Long id, String name, Boolean isGroupChat) {
        this.id = id;
        this.name = name;
        this.isGroupChat = isGroupChat != null ? isGroupChat : false;
    }

    public static ChatRoom createGroupChatRoom(String name) {
        return ChatRoom.builder()
                .name(name)
                .isGroupChat(true)
                .build();
    }

    public boolean isChatRoomMember(Member member) {
        return chatParticipants.stream()
                .anyMatch(chatParticipant -> chatParticipant.getMember().equals(member));
    }

    public boolean hasParticipantsEmail(String email) {
        return chatParticipants.stream()
                .anyMatch(chatParticipant -> chatParticipant.getMember().getEmail().equals(email));
    }

    public Member getMemberByEmail(String email) {
        return chatParticipants.stream()
                .map(ChatParticipant::getMember)
                .filter(member -> Objects.equals(member.getEmail(), email))
                .findAny()
                .orElse(null);
    }
}
