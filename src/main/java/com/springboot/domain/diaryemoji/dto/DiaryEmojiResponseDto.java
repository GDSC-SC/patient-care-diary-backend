package com.springboot.domain.diaryemoji.dto;

import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.Emoji;
import com.springboot.domain.diaryemoji.entity.EmojiCount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class DiaryEmojiResponseDto {

    List<EmojiCount> emojiCounts;
    Emoji myEmojiState;
    @Builder
    public DiaryEmojiResponseDto(List<EmojiCount> emojiCounts, Emoji myEmojiState) {
        this.emojiCounts = emojiCounts;
        this.myEmojiState = myEmojiState;
    }
}
