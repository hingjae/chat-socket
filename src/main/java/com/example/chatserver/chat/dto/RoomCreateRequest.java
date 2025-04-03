package com.example.chatserver.chat.dto;

import lombok.Data;

@Data
public class RoomCreateRequest {
    private String roomName;

    public RoomCreateRequest(String roomName) {
        this.roomName = roomName;
    }
}
