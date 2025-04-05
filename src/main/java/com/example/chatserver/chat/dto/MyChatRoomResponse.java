package com.example.chatserver.chat.dto;

import com.example.chatserver.chat.domain.ChatParticipant;
import com.example.chatserver.chat.domain.ChatRoom;
import lombok.Data;

@Data
public class MyChatRoomResponse {
    private Long roomId;
    private String roomName;
    private Boolean isGroupChat;
    private Long unReadCount;

    public MyChatRoomResponse(ChatParticipant chatParticipant, Long unReadCount) {
        ChatRoom chatRoom = chatParticipant.getChatRoom();
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getName();
        this.isGroupChat = chatRoom.getIsGroupChat();
        this.unReadCount = unReadCount;
    }
}
