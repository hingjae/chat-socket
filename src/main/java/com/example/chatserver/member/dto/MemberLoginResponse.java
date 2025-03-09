package com.example.chatserver.member.dto;

import lombok.Data;

@Data
public class MemberLoginResponse {
    private Long id;
    private String token;

    public MemberLoginResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
