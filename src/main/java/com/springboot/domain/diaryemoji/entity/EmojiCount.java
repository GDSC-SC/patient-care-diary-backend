package com.springboot.domain.diaryemoji.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmojiCount {
    private Emoji emoji;

    private int count;
    @Builder
    public EmojiCount(Emoji emoji, int count) {
        this.emoji = emoji;
        this.count = count;
    }
}
