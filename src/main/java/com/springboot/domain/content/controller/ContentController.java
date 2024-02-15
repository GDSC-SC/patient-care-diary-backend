package com.springboot.domain.content.controller;

import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.dto.ContentUpdateRequestDto;
import com.springboot.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contents")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/create")
    public long save(@RequestBody ContentRequestDto requestDto) {
        return contentService.save(requestDto);
    }
    @GetMapping("/{contentId}")
    public ContentResponseDto findById(@PathVariable Long contentId) {
        return contentService.findById(contentId);
    }
    @PostMapping("/{contentId}")
    public long update(@RequestBody ContentUpdateRequestDto requestDto, @PathVariable Long contentId) {
        return contentService.update(requestDto, contentId);
    }
    @DeleteMapping("/{contentId}")
    public void delete(@PathVariable Long contentId) {
        contentService.delete(contentId);
    }
    @GetMapping("/diary/{diaryId}")
    public List<ContentResponseDto> findByDiary(@PathVariable Long diaryId) {
        return contentService.findByDiary(diaryId);
    }
}
