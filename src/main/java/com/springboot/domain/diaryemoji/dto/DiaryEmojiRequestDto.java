package com.springboot.domain.diaryemoji.dto;

import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.Emoji;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryEmojiRequestDto {
    private String emojiCode;
    private Long memberId;
    private Long diaryId;
}
