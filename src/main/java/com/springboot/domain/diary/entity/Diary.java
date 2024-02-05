package com.springboot.domain.diary.entity;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "diary")
    List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    List<DiaryEmoji> diaryEmojis = new ArrayList<>();

    @Builder
    public Diary(Member member, LocalDate date) {
        this.member = member;
        this.date = date;
    }
}
