package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.*;
import com.example.chatserver.chat.service.ChatService;
import com.example.chatserver.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    // 그룹 채팅방 개설
    @PostMapping(("/room/group/create"))
    public ApiResponse<?> createGroupRoom(@RequestBody GroupChatRoomCreateRequest request) {
        chatService.createGroupRoom(request);
        return ApiResponse.ok("create group room success");
    }

    // 그룹 채팅 목록 조회
    @GetMapping("/room/group/list")
    public ApiResponse<?> getGroupChatRooms() {
        ChatRoomResponseList groupChatRooms = chatService.getGroupChatRooms();

        return ApiResponse.ok(groupChatRooms);
    }

    // 그룹채팅방 참여
    @PostMapping("/room/group/{roomId}/join")
    public ApiResponse<?> joinGroupChatRoom(@PathVariable Long roomId) {
        chatService.addParticipantToGroupChat(roomId);

        return ApiResponse.ok("join group room success");
    }

    // 채팅방 접속 시 이전 대화이력 불러오기
    @GetMapping("/history/{roomId}")
    public ApiResponse<?> getChatHistory(@PathVariable Long roomId) {
        ChatMessageDtoList chatHistories = chatService.getChatHistories(roomId);
        return ApiResponse.ok(chatHistories);
    }

    // 채팅 메시지 읽음처리
    @PostMapping("/room/{roomId}/read")
    public ApiResponse<?> messageRead(@PathVariable Long roomId) {
        chatService.messageRead(roomId);
        return ApiResponse.ok("message read success");
    }

    // 내 채팅목록 조회 : roomId, roomName, 그룹채팅여부, 메시지읽음개수
    @GetMapping("/my/rooms")
    public ApiResponse<?> getMyChatRooms() {
        MyChatRoomResponseList myChatRoomResponseList = chatService.getMyChatRooms();
        return ApiResponse.ok(myChatRoomResponseList);
    }

    // 그룹채팅방 나가기
    @DeleteMapping("/room/group/{roomId}/leave")
    public ApiResponse<?> leaveGroupChatRoom(@PathVariable Long roomId) {
        chatService.leaveGroupChatRoom(roomId);
        return ApiResponse.ok("leave group room success");
    }

    // 개인 채팅방 개설 또는 기존 roomId return
    @PostMapping("/room/private/create")
    public ApiResponse<?> getOrCreatePrivateChatRoom(@RequestBody PrivateChatRoomCreateRequest request) {
        Long roomId = chatService.getOrCreatePrivateRoom(request.getMemberId());
        return ApiResponse.ok(roomId);
    }
}
