package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessageDtoList;
import com.example.chatserver.chat.dto.ChatRoomResponseList;
import com.example.chatserver.chat.dto.RoomCreateRequest;
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
    public ApiResponse<?> createGroupRoom(@RequestBody RoomCreateRequest request) {
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

    @GetMapping("/history/{roomId}")
    public ApiResponse<?> getChatHistory(@PathVariable Long roomId) {
        ChatMessageDtoList chatHistories = chatService.getChatHistories(roomId);
        return ApiResponse.ok(chatHistories);
    }
}
