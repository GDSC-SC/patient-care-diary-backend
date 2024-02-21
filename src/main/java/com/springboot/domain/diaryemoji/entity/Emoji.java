package com.springboot.domain.diaryemoji.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Emoji {

    GOOD("E001"),
    LOVE("E002"),
    CHECK("E003"),
    NONE("none");

    private final String code;
    public static Emoji findByCode(String code) {
        for (Emoji emoji : values()) {
            if (emoji.code.equals(code)) {
                return emoji;
            }
        }
        return null;
    }
}
