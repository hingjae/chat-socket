package com.example.chatserver.member.dto;

import com.example.chatserver.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class MemberListResponse {

    private List<MemberResponse> members;

    public MemberListResponse(List<Member> members) {
        this.members = members.stream()
                .map(member -> new MemberResponse(member.getId(), member.getEmail(), member.getName()))
                .toList();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class MemberResponse {
        private Long id;
        private String email;
        private String name;
    }
}
