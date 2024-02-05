package com.springboot.domain.diary.controller;

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
@RequestMapping("/diarys")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create")
    public long save(@RequestBody DiaryRequestDto requestDto) {
        return diaryService.save(requestDto);
    }
    @GetMapping("/{diary_id}")
    public DiaryResponseDto findById(@PathVariable Long diary_id) {
        return diaryService.findById(diary_id);
    }

//    @PutMapping("/{diary_id}")
//    public DiaryResponseDto update(@RequestBody DiaryRequestDto requestDto) {
//        return diaryService.update();
//    }
    @DeleteMapping("/{diary_id}")
    public void delete(@PathVariable Long diary_id) {
        diaryService.delete(diary_id);
    }
    @GetMapping("/my")
    public List<DiaryResponseDto> findByMember(@PathVariable Long member_id) {
        return diaryService.findByMember(member_id);
    }
    @GetMapping("/my")
    public List<DiaryResponseDto> findAll() {
        return diaryService.findAll();
    }

}
