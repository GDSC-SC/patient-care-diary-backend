package com.springboot.domain.diary.entity;

import com.springboot.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByMember(Member member);

    Optional<Diary> findByMemberAndDate(Member member, LocalDate date);

}
