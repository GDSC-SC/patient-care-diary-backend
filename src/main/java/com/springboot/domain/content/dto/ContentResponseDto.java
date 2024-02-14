package com.springboot.domain.content.dto;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diary.entity.Diary;
import lombok.Builder;

public class ContentResponseDto {
    private Long id;

    private Long categoryId;

    private Boolean done;

    private String photoUrl;

    private String text;

    @Builder
    public ContentResponseDto(Content entity) {
        this.id = entity.getId();
        this.categoryId = entity.getCategory().getId();
        this.done = entity.getDone();
        this.photoUrl = entity.getPhotoUrl();
        this.text = entity.getText();
    }
}
