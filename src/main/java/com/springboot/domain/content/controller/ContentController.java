package com.springboot.domain.content.controller;

import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.dto.ContentUpdateRequestDto;
import com.springboot.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/create")
    public long save(@RequestBody ContentRequestDto requestDto) throws IOException {
        return contentService.save(requestDto);
    }
//    @PostMapping("/image")
//    public long saveImage(@Reques) throws IOException {
//        return contentService.save(requestDto);
//    }
    @GetMapping("/{contentId}")
    public ContentResponseDto findById(@PathVariable Long contentId) {
        return contentService.findById(contentId);
    }
    @PostMapping("/{contentId}")
    public long update(ContentUpdateRequestDto requestDto, @PathVariable Long contentId) throws IOException{
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
