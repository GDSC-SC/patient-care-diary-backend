package com.springboot.domain.diaryemoji.controller;

import com.springboot.domain.diary.dto.DiaryRequestDto;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiRequestDto;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
import com.springboot.domain.diaryemoji.service.DiaryEmojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/emoji")
public class DiaryEmojiController {

    private final DiaryEmojiService diaryEmojiService;
    @PutMapping("/update")
    public long update(@RequestBody DiaryEmojiRequestDto requestDto) {
        return diaryEmojiService.update(requestDto);
    }
    @DeleteMapping("/{diaryId}/{emojiCode}")
    public void delete(@PathVariable Long diaryId, @PathVariable String emojiCode) {
        diaryEmojiService.delete(diaryId, emojiCode);
    }

    @GetMapping("/{diaryId}")
    public DiaryEmojiResponseDto findByDiary(@PathVariable Long diaryId) {
        return diaryEmojiService.findByDiary(diaryId);
    }
}
