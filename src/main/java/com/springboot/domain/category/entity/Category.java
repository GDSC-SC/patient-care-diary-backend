package com.springboot.domain.category.entity;

import com.springboot.domain.content.entity.Content;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(nullable = false)
    private CategoryCode categoryCode;

    @Column(nullable = false)
    private String midCategory;

    @Column(nullable = false)
    private String color;

    @Setter
    @Column(nullable = false)
    private Boolean visible;

    @OneToMany(mappedBy = "category")
    List<Content> contents = new ArrayList<>();

    @Builder
    public Category(Member member, CategoryCode categoryCode, String midCategory, String color){
        this.member = member;
        this.categoryCode = categoryCode;
        this.midCategory = midCategory;
        this.color = color;
        this.visible = true;
    }

    public void update(CategoryCode categoryCode, String midCategory, String color){
        this.categoryCode = categoryCode;
        this.midCategory = midCategory;
        this.color = color;
    }

}
