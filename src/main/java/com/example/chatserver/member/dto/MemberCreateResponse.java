package com.example.chatserver.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateResponse {
    private final Long id;

    @Builder
    public MemberCreateResponse(Long id) {
        this.id = id;
    }
}
