package com.springboot.global.error;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.entity.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    //다이어리
    DIARY_NOT_FOUND(404, "D001", "해당 다이어리를 찾을 수 없습니다."),

    //멤버
    MEMBER_NOT_FOUND(404, "M001", "해당 유저를 찾을 수 없습니다."),

    //이모지
    EMOJI_NOT_FOUND(404, "E001", "해당 이모지를 찾을 수 없습니다."),
    EMOJI_CODE_NOT_FOUND(400, "E002", "해당 이모지 코드가 존재하지 않습니다."),
    EMOJI_DUPLICATE_REQUEST(409, "E003", "중복된 이모지 요청입니다.");

    private final int status;
    private final String code;
    private final String message;
}
