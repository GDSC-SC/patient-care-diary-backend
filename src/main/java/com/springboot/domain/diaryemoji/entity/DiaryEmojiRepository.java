package com.springboot.domain.diaryemoji.entity;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryEmojiRepository extends JpaRepository<DiaryEmoji, Long> {
    Optional<DiaryEmoji> findByMemberAndDiary(Member member, Diary diary);
}
