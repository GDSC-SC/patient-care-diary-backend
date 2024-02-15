package com.springboot.domain.content.entity;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findContentByDiaryAndCategory(Diary diary, Category category);
}
