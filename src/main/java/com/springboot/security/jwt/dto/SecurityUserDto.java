package com.springboot.security.jwt.dto;

import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SecurityUserDto {
    private String name;
    private String email;
    private String picture;

    private Role role;

    @Builder
    public SecurityUserDto(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
        this.role = member.getRole();
    }

}
