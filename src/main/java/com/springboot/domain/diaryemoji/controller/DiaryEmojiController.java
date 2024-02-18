package com.springboot.domain.diaryemoji.controller;

import com.springboot.domain.diary.dto.DiaryRequestDto;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiRequestDto;
import com.springboot.domain.diaryemoji.service.DiaryEmojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emoji")
public class DiaryEmojiController {

    private final DiaryEmojiService diaryEmojiService;
    @PostMapping("/create")
    public long save(@RequestBody DiaryEmojiRequestDto requestDto) {
        return diaryEmojiService.save(requestDto);
    }
    @DeleteMapping("/{diaryId}")
    public void delete(@PathVariable Long diaryId) {
        diaryEmojiService.delete(diaryId);
    }
}
