package com.springboot.domain.content.controller;

import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.dto.ContentUpdateRequestDto;
import com.springboot.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contents")
public class ContentController {

    private final ContentService contentService;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public long save(@RequestPart ContentRequestDto requestDto, @RequestPart MultipartFile image) throws IOException {
        System.out.println(requestDto);
        System.out.println(image);
        return contentService.save(requestDto, image);
    }
    @GetMapping("/{contentId}")
    public ContentResponseDto findById(@PathVariable Long contentId) {
        return contentService.findById(contentId);
    }
    @PutMapping(value = "/{contentId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public long update(@RequestPart ContentUpdateRequestDto requestDto,
                       @RequestPart MultipartFile image, @PathVariable Long contentId) throws IOException{
        return contentService.update(requestDto, image, contentId);
    }
    @DeleteMapping("/{contentId}")
    public void delete(@PathVariable Long contentId) throws IOException {
        contentService.delete(contentId);
    }
    @GetMapping("/diary/{diaryId}")
    public List<ContentResponseDto> findByDiary(@PathVariable Long diaryId) {
        return contentService.findByDiary(diaryId);
    }
}
