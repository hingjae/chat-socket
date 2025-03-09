package com.example.chatserver.member.dto;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequest {
    private String name;
    private String email;
    private String password;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}
