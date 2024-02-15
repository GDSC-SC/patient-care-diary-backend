package com.springboot.domain.content.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentUpdateRequestDto {
    private Boolean done;

    private String photoUrl;

    private String text;
}
