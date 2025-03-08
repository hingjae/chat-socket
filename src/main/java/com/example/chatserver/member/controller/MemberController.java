package com.example.chatserver.member.controller;

import com.example.chatserver.common.ApiResponse;
import com.example.chatserver.member.dto.MemberCreateRequest;
import com.example.chatserver.member.dto.MemberCreateResponse;
import com.example.chatserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ApiResponse<?> create(@RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.create(request);
        return ApiResponse.of(HttpStatus.CREATED, response.getId());
    }
}
