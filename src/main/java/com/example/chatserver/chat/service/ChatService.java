package com.example.chatserver.chat.service;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatParticipant;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.domain.ReadStatus;
import com.example.chatserver.chat.dto.ChatMessageDto;
import com.example.chatserver.chat.repository.ChatMessageRepository;
import com.example.chatserver.chat.repository.ChatParticipantRepository;
import com.example.chatserver.chat.repository.ChatRoomRepository;
import com.example.chatserver.chat.repository.ReadStatusRepository;
import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ReadStatusRepository readStatusRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveMessage(Long roomId, ChatMessageDto chatMessageDto) {
        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("cannot find chat room with id: " + roomId));

        // 보낸사람 조회
        Member member = memberRepository.findByEmail(chatMessageDto.getSender())
                .orElseThrow(() -> new EntityNotFoundException("cannot find member with email: " + chatMessageDto.getSender()));

        // 메세지 저장.
        ChatMessage chatMessage = ChatMessage.of(chatRoom, member, chatMessageDto.getContent());
        chatMessageRepository.save(chatMessage);

        // 채팅방 참여자 별로 ReadStatus 저장.
        // 채팅마다 읽음 여부를 생성하는군.
        List<ChatParticipant> chatParticipants = chatParticipantRepository.findByChatRoom(chatRoom);
        for (ChatParticipant chatParticipant : chatParticipants) {
            ReadStatus readStatus = ReadStatus.of(chatRoom, member, chatMessage, chatParticipant);
            readStatusRepository.save(readStatus);
        }
    }
}
