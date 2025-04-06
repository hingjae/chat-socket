package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    @Modifying(flushAutomatically = true)
    @Query("delete from ChatRoom chatRoom where chatRoom.id = :id")
    void deleteByIdJpql(Long id);

    @Query(value = """
        select cp.chat_room_id
        from chat_participant cp
        join chat_room cr on cp.chat_room_id = cr.id
        where cp.member_id in (:memberId, :targetMemberId) and cr.is_group_chat = false
        group by cp.chat_room_id
        having count(distinct cp.member_id) = 2
    """, nativeQuery = true)
    Optional<Long> findPrivateRoom(Long memberId, Long targetMemberId);
}
