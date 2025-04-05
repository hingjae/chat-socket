package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select chatMessage" +
            " from ChatMessage chatMessage" +
            " join fetch chatMessage.member" +
            " where chatMessage.chatRoom = :chatRoom" +
            " order by chatMessage.createdTime asc")
    List<ChatMessage> findByChatRoomWithMemberOrderByCreatedTimeAsc(ChatRoom chatRoom);

    @Modifying
    @Query("delete from ChatMessage chatMessage where chatMessage.chatRoom = :chatRoom")
    void deleteAllByChatRoom(ChatRoom chatRoom);
}
