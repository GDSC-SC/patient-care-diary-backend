package com.springboot.domain.diaryemoji.dto;

import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.Emoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryEmojiResponseDto {

    private Emoji emoji;

    private int count;
    @Builder
    public DiaryEmojiResponseDto(Emoji emoji, int count) {
        this.emoji = emoji;
        this.count = count;
    }
}
