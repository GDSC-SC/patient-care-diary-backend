package com.springboot.domain.category.dto;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private long id;
    private CategoryCode category;
    private String subtitle;
    private String color;
    private Boolean visible;

    @Builder
    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.category = entity.getCategoryCode();
        this.subtitle = entity.getSubtitle();
        this.color = entity.getColor();
        this.visible = entity.getVisible();
    }
}
