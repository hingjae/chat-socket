package com.example.chatserver.member.service;

import com.example.chatserver.member.MemberFactory;
import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.domain.Role;
import com.example.chatserver.member.dto.MemberCreateRequest;
import com.example.chatserver.member.dto.MemberCreateResponse;
import com.example.chatserver.member.dto.MemberLoginRequest;
import com.example.chatserver.member.dto.MemberLoginResponse;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void clear() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("이메일이 이미 존재하면 예외를 던진다.")
    @Test
    void alreadyExist() {
        Member user = MemberFactory.createMember();

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

    @DisplayName("존재하지 않는 아이디로 로그인하면 실패한다.")
    @Test
    void loginFail1() {
        MemberLoginRequest request = new MemberLoginRequest("user1@gmail.com", "123456");

        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("틀린 비밀번호로 로그인하면 실패한다.")
    @Test
    void loginFail2() {
        Member member = MemberFactory.createMember("user1@gmail.com", passwordEncoder.encode("123456"), "user1");
        memberRepository.save(member);

        MemberLoginRequest request = new MemberLoginRequest("user1@gmail.com", "1234567");

        assertThatThrownBy(() -> memberService.login(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("사용자는 로그인 한다.")
    @Test
    void login() {
        Member member = MemberFactory.createMember("user1@gmail.com", passwordEncoder.encode("123456"), "user1");
        memberRepository.save(member);

        MemberLoginRequest request = new MemberLoginRequest("user1@gmail.com", "123456");

        MemberLoginResponse response = memberService.login(request);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getToken()).isNotBlank();
    }
}