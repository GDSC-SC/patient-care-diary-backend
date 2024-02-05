package com.springboot.domain.diary.dto;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.member.entity.Member;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DiaryResponseDto {

    private Long id;
    private Member member;
    private LocalDate date;
    private List<Content> contents;
    private List<DiaryEmoji> diaryEmojis;
    @Builder
    public DiaryResponseDto(Diary entity) {
        this.id = entity.getId();
        this.member = entity.getMember();
        this.date = entity.getDate();
        this.contents = entity.getContents();
        this.diaryEmojis = entity.getDiaryEmojis();
    }
}
