package com.springboot.domain.diary.dto;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
import com.springboot.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class DiaryListResponseDto {

    private Long id;
    private String name;
    private LocalDate date;
    @Setter
    private List<DiaryEmojiResponseDto> diaryEmojis;
    //컨텐츠에서 카테고리만 보여주는 방향 (?)
    @Builder
    public DiaryListResponseDto(Diary entity) {
        this.id = entity.getId();
        this.name = entity.getMember().getName();
        this.date = entity.getDate();
    }

}
