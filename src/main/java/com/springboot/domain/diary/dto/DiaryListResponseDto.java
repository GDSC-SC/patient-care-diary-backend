package com.springboot.domain.diary.dto;

import com.springboot.domain.category.dto.CategoryResponseDto;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
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
public class DiaryListResponseDto {

    private Long id;
    private MemberResponseDto member;
    private LocalDate date;
    @Setter
    private List<DiaryEmojiResponseDto> diaryEmojis;

    //컨텐츠에서 카테고리만 보여주는 방향
    @Setter
    private List<CategoryResponseDto> categories;
    @Builder
    public DiaryListResponseDto(Diary entity) {
        this.id = entity.getId();
        this.member = new MemberResponseDto(entity.getMember());
        this.date = entity.getDate();
        this.categories = entity.getContents().stream()
                .map(content -> new CategoryResponseDto(content.getCategory()))
                .collect(Collectors.toList());
    }

}
