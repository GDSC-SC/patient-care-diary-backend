package com.springboot.domain.diaryemoji.entity;

import com.springboot.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryEmojiRepository extends JpaRepository<DiaryEmoji, Long> {
}
