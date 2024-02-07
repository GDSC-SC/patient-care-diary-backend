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
public class DiaryResponseDto {
    private Long id;
    private String name;
    private LocalDate date;
    @Setter
    private List<DiaryEmojiResponseDto> diaryEmojis;
    //컨텐츠를 다 보여주는 방향
    @Builder
    public DiaryResponseDto(Diary entity) {
        this.id = entity.getId();
        this.name = entity.getMember().getName();
        this.date = entity.getDate();
    }

}
