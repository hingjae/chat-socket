package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatParticipant;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    List<ChatParticipant> findByChatRoom(ChatRoom chatRoom);

    boolean existsByChatRoomAndMember(ChatRoom chatRoom, Member member);

    @Query("select chatParticipant" +
            " from ChatParticipant chatParticipant" +
            " join fetch chatParticipant.chatRoom " +
            " where chatParticipant.member = :member")
    List<ChatParticipant> findAllByMemberWithChatRoom(Member member);

    Optional<ChatParticipant> findByChatRoomAndMember(ChatRoom chatRoom, Member member);
}
