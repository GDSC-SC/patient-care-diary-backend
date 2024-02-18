package com.springboot.domain.diary.controller;

import com.springboot.domain.diary.dto.DiaryListResponseDto;
import com.springboot.domain.diary.dto.DiaryRequestDto;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diarys")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create")
    public long save(@RequestBody DiaryRequestDto requestDto) {
        return diaryService.save(requestDto);
    }
    @GetMapping("/{diaryId}")
    public DiaryResponseDto findById(@PathVariable Long diaryId) {
        return diaryService.findById(diaryId);
    }
    @GetMapping("/date/{date}")
    public DiaryResponseDto findById(@PathVariable String date) {
        return diaryService.findByMemberAndDate(date);
    }
    @DeleteMapping("/{diaryId}")
    public void delete(@PathVariable Long diaryId) {
        diaryService.delete(diaryId);
    }
    @GetMapping("/my")
    public List<DiaryListResponseDto> findMyDiary() {
        return diaryService.findMyDiary();
    }
    @GetMapping("/all")
    public List<DiaryListResponseDto> findAll() {
        return diaryService.findAll();
    }

}
