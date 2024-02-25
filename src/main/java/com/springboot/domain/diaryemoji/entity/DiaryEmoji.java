package com.springboot.domain.diaryemoji.entity;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DiaryEmoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID")
    private Diary diary;

    @Setter
    @Column(nullable = false)
    private Emoji emoji;

    @Builder
    public DiaryEmoji(Member member, Diary diary, Emoji emoji) {
        this.member = member;
        this.diary = diary;
        this.emoji = emoji;
    }

}
