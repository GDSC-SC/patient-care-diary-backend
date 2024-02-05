package com.springboot.domain.diary.dto;

import com.springboot.domain.diary.entity.Diary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {

    private long member_id;
    private LocalDate date;

}
