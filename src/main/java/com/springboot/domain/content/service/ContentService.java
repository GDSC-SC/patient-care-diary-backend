package com.springboot.domain.content.service;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.entity.Content;
import com.springboot.domain.content.entity.ContentRepository;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final DiaryRepository diaryRepository;
    private final CategoryRepository categoryRepository;
    public long save(ContentRequestDto requestDto) {
        Diary diary = diaryRepository.findById(requestDto.getDiaryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + requestDto.getDiaryId()));
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));
        Content content = Content.builder()
                .diary(diary)
                .category(category)
                .done(requestDto.getDone())
                .photoUrl(requestDto.getPhotoUrl())
                .text(requestDto.getText())
                .build();
        return contentRepository.save(content).getId();
    }

    public ContentResponseDto findById(Long id) {
        Content entity = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        ContentResponseDto responseDto = new ContentResponseDto(entity);
        return responseDto;
    }

    //구현해야함
    public long update(Long id) {
        Content entity = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        return id;
    }

    public void delete(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        contentRepository.delete(content);
    }

    public List<ContentResponseDto> findByDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + diaryId));
        List<Content> contents = diary.getContents();
        return contents.stream()
                .map(content -> {
                    ContentResponseDto responseDto = new ContentResponseDto(content);
                    return responseDto;
                })
                .collect(Collectors.toList());
    }
}
