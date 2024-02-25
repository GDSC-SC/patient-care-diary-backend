package com.springboot.domain.member.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRequestDto {
    private String gender;

    private String illness;

    private String type;
    private String description;
}
