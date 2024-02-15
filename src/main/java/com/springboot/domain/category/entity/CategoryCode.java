package com.springboot.domain.category.entity;

import com.springboot.domain.diaryemoji.entity.Emoji;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryCode {

    FOOD("C001", "음식", "food"),
    MEDICINE("C002", "약", "medicine"),
    DIARY("C003", "오늘의 일기", "today diary"),
    EXERCISE("C004", "운동", "exercise"),
    ILLNESS("C005", "질병", "about illness");

    private final String code;
    private final String korName;
    private final String engName;

    public static CategoryCode findByCode(String code) {
        for (CategoryCode category : values()) {
            if (category.code.equals(code)) {
                return category;
            }
        }
        return null;
    }
}
