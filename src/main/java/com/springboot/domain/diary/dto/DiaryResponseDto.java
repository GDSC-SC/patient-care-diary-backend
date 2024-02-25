package com.springboot.domain.diary.dto;

import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
import com.springboot.domain.diaryemoji.entity.Emoji;
import com.springboot.domain.member.dto.MemberResponseDto;
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
    private MemberResponseDto member;
    private LocalDate date;

    //컨텐츠를 다 보여주는 방향
    private List<ContentResponseDto> contents;

    @Builder
    public DiaryResponseDto(Diary entity) {
        this.id = entity.getId();
        this.member = new MemberResponseDto(entity.getMember());
        this.date = entity.getDate();
        this.contents = entity.getContents().stream()
                .map(ContentResponseDto::new)
                .collect(Collectors.toList());
    }

}
