package com.springboot.domain.content.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ContentUpdateRequestDto {
    private Boolean done;

    private String text;
}
