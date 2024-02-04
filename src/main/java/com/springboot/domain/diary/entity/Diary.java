package com.springboot.domain.diary.entity;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String date;

    @OneToMany(mappedBy = "diary")
    List<Content> contents = new ArrayList<>();

    @OneToMany(mappedBy = "diary")
    List<DiaryEmoji> diaryEmojis = new ArrayList<>();
}
