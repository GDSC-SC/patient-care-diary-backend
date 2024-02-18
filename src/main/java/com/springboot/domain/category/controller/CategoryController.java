package com.springboot.domain.category.controller;

import com.springboot.domain.category.dto.CategoryRequestDto;
import com.springboot.domain.category.dto.CategoryResponseDto;
import com.springboot.domain.category.service.CategoryService;
import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping("/create")
    public long save(@RequestBody CategoryRequestDto requestDto) {
        return categoryService.save(requestDto);
    }
    @GetMapping("/{categoryId}")
    public CategoryResponseDto findById(@PathVariable Long categoryId) {
        return categoryService.findById(categoryId);
    }
    @PutMapping("/{categoryId}")
    public long update(@RequestBody CategoryRequestDto requestDto, @PathVariable Long categoryId) {
        return categoryService.update(requestDto, categoryId);
    }
    @DeleteMapping("/{categoryId}")
    public void delete(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
    }
    @PutMapping("/visible/{categoryId}")
    public void setVisibility(@PathVariable Long categoryId) {
        categoryService.setVisibility(categoryId);
    }
    @GetMapping("/my")
    public List<CategoryResponseDto> findMyCategory() {
        return categoryService.findMyCategory();
    }
}
