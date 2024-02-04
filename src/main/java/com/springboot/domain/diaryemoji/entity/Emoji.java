package com.springboot.domain.diaryemoji.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emoji {

    GOOD("😊", "좋아요."),
    LOVE("❤️", "사랑해요."),
    CHECK("✔️", "확인했어요.");

    private final String symbol;
    private final String description;
}
