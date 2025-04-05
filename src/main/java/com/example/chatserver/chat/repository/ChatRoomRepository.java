package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByIsGroupChatTrue();

    @Query("select chatRoom" +
            " from ChatRoom chatRoom" +
            " join fetch chatRoom.chatParticipants chatParticipants" +
            " join fetch chatParticipants.member" +
            " where chatRoom.id = :id")
    Optional<ChatRoom> findByIdWithChatParticipantsAndMember(Long id);
}
