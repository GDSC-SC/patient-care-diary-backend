package com.springboot.domain.category.entity;

import com.springboot.domain.diaryemoji.entity.Emoji;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryCode {

    FOOD("C001", "음식", "Food"),
    MEDICINE("C002", "약", "Medicine"),
    DIARY("C003", "오늘의 일기", "Today Diary"),
    EXERCISE("C004", "운동", "Exercise"),
    ILLNESS("C005", "질병", "About Illness"),
    MEMO("C006", "메모", "Memo");

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
