package com.springboot.domain.member.service;

import com.springboot.domain.member.dto.MemberRequestDto;
import com.springboot.domain.member.dto.MemberResponseDto;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.EntityNotFoundException;
import com.springboot.security.jwt.dto.SecurityUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public long update(MemberRequestDto requestDto) {
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));
        member.signUp(requestDto);
        return member.getId();
    }

    public MemberResponseDto findMy() {
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));
        return new MemberResponseDto(member);
    }
}
