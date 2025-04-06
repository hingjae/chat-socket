package com.example.chatserver.chat.dto;

import lombok.Data;

@Data
public class PrivateChatRoomCreateRequest {

    private Long memberId;

    public PrivateChatRoomCreateRequest(Long memberId) {
        this.memberId = memberId;
    }
}
