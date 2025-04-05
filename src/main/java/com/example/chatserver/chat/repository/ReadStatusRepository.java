package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.domain.ReadStatus;
import com.example.chatserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, Long> {
    List<ReadStatus> findByChatRoomAndMember(ChatRoom chatRoom, Member member);

    long countByChatRoomAndMemberAndIsReadFalse(ChatRoom chatRoom, Member member);

    List<ReadStatus> findByChatRoom(ChatRoom chatRoom);

    @Modifying
    @Query("delete from ReadStatus readStatus where readStatus.chatRoom = :chatRoom")
    void deleteAllByChatRoom(ChatRoom chatRoom);
}
