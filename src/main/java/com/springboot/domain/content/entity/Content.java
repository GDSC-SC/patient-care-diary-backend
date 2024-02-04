package com.springboot.domain.content.entity;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private Boolean done;

    @Column(nullable = false)
    private String photoUrl;

    @Column(nullable = false)
    private String text;

}
