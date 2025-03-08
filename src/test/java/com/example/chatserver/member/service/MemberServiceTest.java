package com.example.chatserver.member.service;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.domain.Role;
import com.example.chatserver.member.dto.MemberCreateRequest;
import com.example.chatserver.member.dto.MemberCreateResponse;
import com.example.chatserver.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    void clear() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("이메일이 이미 존재하면 예외를 던진다.")
    @Test
    void alreadyExist() {
        Member user = Member.builder()
                .name("user1")
                .password("123456")
                .email("user1@gmail.com")
                .role(Role.USER)
                .build();

        memberRepository.save(user);

        MemberCreateRequest request = new MemberCreateRequest("user1", "user1@gmail.com", "123456");

        assertThatThrownBy(() -> memberService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일입니다.");
    }

    @DisplayName("사용자는 회원가입한다.")
    @Test
    void join() {
        MemberCreateRequest request = new MemberCreateRequest("user1", "user1@gmail.com", "123456");

        MemberCreateResponse memberCreateResponse = memberService.create(request);

        assertThat(memberCreateResponse.getId()).isNotNull();
    }
}