package com.springboot.domain.content.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class ContentRequestDto {

    private Long diaryId;

    private Long categoryId;

    private Boolean done;

    private String text;

}
