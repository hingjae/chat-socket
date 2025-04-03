package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.RoomCreateRequest;
import com.example.chatserver.chat.service.ChatService;
import com.example.chatserver.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
