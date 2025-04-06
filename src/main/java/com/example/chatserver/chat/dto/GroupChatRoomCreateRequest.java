package com.example.chatserver.chat.dto;

import lombok.Data;

@Data
public class GroupChatRoomCreateRequest {
    private String roomName;

    public GroupChatRoomCreateRequest(String roomName) {
        this.roomName = roomName;
    }
}
