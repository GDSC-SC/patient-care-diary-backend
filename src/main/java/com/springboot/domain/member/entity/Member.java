package com.springboot.domain.member.entity;

import com.springboot.domain.category.entity.Category;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.member.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column(nullable = false)
    private Role role;

    @Column
    private String refreshToken;

    @Column
    private String gender;

    @Column
    private String illness;

    @Column
    private String type;

    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Member(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void authorizeUser() {
        this.role = Role.USER;
    }

    public void signUp(MemberRequestDto requestDto) {
        this.gender = requestDto.getGender();
        this.illness = requestDto.getIllness();
        this.type = requestDto.getType();
    }
}
