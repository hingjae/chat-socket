package com.example.chatserver.chat.dto;

import com.example.chatserver.chat.domain.ChatRoom;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomResponseList {

    private List<ChatRoomResponse> chatRoomResponses;

    @Data
    static class ChatRoomResponse {
        private Long roomId;
        private String roomName;

        public ChatRoomResponse(ChatRoom chatRoom) {
            this.roomId = chatRoom.getId();
            this.roomName = chatRoom.getName();
        }
    }

    public ChatRoomResponseList(List<ChatRoom> chatRooms) {
        this.chatRoomResponses = chatRooms.stream()
                                    .map(ChatRoomResponse::new)
                                    .toList();
    }
}
