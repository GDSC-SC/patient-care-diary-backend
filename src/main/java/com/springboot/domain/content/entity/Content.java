package com.springboot.domain.content.entity;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DIARY_ID")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(nullable = false)
    private Boolean done;

    private String photoUrl;

    private String text;

    @Builder
    public Content(Diary diary, Category category, Boolean done, String photoUrl, String text) {
        this.diary = diary;
        this.category = category;
        this.done = done;
        this.photoUrl = photoUrl;
        this.text = text;
    }

}
