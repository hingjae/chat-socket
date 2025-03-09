package com.example.chatserver.member;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.domain.Role;

public class MemberFactory {
    public static Member createMember() {
        return Member.builder()
                .name("user1")
                .password("123456")
                .email("user1@gmail.com")
                .role(Role.USER)
                .build();
    }

    public static Member createMember(String email, String password, String name) {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
