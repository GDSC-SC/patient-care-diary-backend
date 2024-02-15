package com.springboot.domain.category.dto;

import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {

    private String categoryCode;
    private String subtitle;
    private String color;

}
