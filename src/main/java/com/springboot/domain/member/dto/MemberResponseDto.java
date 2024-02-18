package com.springboot.domain.member.dto;

import com.springboot.domain.member.entity.Member;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
public class MemberResponseDto {

    private String name;
    private String email;
    private String picture;
    private String gender;

    private String illness;

    private String type;

    @Builder
    public MemberResponseDto(Member entity){
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.gender = entity.getGender();
        this.illness = entity.getIllness();
        this.type = entity.getType();
    }
}
