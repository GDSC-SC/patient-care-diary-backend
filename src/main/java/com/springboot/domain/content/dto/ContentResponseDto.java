package com.springboot.domain.content.dto;

import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diary.entity.Diary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentResponseDto {

    private String category;
    private String subtitle;
    private String color;

    private Boolean done;

    private String photoUrl;

    private String text;

    @Builder
    public ContentResponseDto(Content entity) {
        this.category = entity.getCategory().getCategoryCode().getKorName();
        this.subtitle = entity.getCategory().getSubtitle();
        this.color = entity.getCategory().getColor();
        this.done = entity.getDone();
        this.photoUrl = entity.getPhotoUrl();
        this.text = entity.getText();
    }
}
