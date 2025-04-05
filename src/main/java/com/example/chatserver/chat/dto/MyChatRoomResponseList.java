package com.example.chatserver.chat.dto;

import lombok.Data;

import java.util.List;

@Data
public class MyChatRoomResponseList {

    private List<MyChatRoomResponse> myChatRooms;

    public MyChatRoomResponseList(List<MyChatRoomResponse> myChatRooms) {
        this.myChatRooms = myChatRooms;
    }
}
