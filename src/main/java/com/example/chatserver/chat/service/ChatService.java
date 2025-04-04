package com.example.chatserver.chat.service;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatParticipant;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.domain.ReadStatus;
import com.example.chatserver.chat.dto.ChatMessageDto;
import com.example.chatserver.chat.dto.ChatRoomResponseList;
import com.example.chatserver.chat.dto.RoomCreateRequest;
import com.example.chatserver.chat.repository.ChatMessageRepository;
import com.example.chatserver.chat.repository.ChatParticipantRepository;
import com.example.chatserver.chat.repository.ChatRoomRepository;
import com.example.chatserver.chat.repository.ReadStatusRepository;
import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 그룹 채팅방 개설
    // 채팅방을 만든 사람은 자동으로 채팅방에 참여
    @Transactional
    public void createGroupRoom(RoomCreateRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("cannot find member with email: " + email));

        // 채팅방 생성
        ChatRoom chatRoom = ChatRoom.createGroupChatRoom(request.getRoomName());
        chatRoomRepository.save(chatRoom);

        // 채탕참여자로 개설자를 추가.
        ChatParticipant chatParticipant = ChatParticipant.of(chatRoom, member);
        chatParticipantRepository.save(chatParticipant);
    }

    public ChatRoomResponseList getGroupChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findByIsGroupChatTrue();
        return new ChatRoomResponseList(chatRooms);
    }

    @Transactional
    public void addParticipantToGroupChat(Long roomId) {
        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("cannot find chat room with id: " + roomId));

        // member 조회
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("cannot find member with email: " + email));

        // 이미 참여자인지 검증
        boolean exists = chatParticipantRepository.existsByChatRoomAndMember(chatRoom, member);

        if (!exists) {
            ChatParticipant chatParticipant = ChatParticipant.of(chatRoom, member);
            chatParticipantRepository.save(chatParticipant);
        }
    }
}
