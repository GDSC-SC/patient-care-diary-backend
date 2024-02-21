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
    private String categoryCode;
    private String category;
    private String midCategory;
    private String color;
    private Boolean visible;

    @Builder
    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.categoryCode = entity.getCategoryCode().getCode();
        this.category = entity.getCategoryCode().getEngName();
        this.midCategory = entity.getMidCategory();
        this.color = entity.getColor();
        this.visible = entity.getVisible();
    }
}
