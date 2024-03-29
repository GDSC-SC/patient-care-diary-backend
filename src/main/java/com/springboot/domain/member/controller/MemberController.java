package com.springboot.domain.member.controller;

import com.springboot.domain.member.dto.MemberRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService;
    @PutMapping("/sign-up")
    public long update(@RequestBody MemberRequestDto requestDto){
        return memberService.update(requestDto);

    }
    @GetMapping("/my")
    public MemberResponseDto findMy() {
        return memberService.findMy();
    }
}
