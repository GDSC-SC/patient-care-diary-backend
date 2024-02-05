package com.springboot.domain.diary.service;

import com.springboot.domain.diary.dto.DiaryRequestDto;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.security.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    public long save(DiaryRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + requestDto.getMember_id()));;

        Diary diary = Diary.builder()
                .member(member)
                .date(requestDto.getDate())
                .build();
//        SessionUser user = (SessionUser)httpSession.getAttribute("user");
//        Member member = memberRepository.findByEmail(user.getEmail())
//                .orElseThrow(() -> new IllegalArgumentException("해당 email을 가진 member가 없습니다. id=" + user.getEmail()));
//        posts.setMember(member);

        return diaryRepository.save(diary).getId();
    }

    public DiaryResponseDto findById(Long id) {
        Diary entity = diaryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 없습니다. id="+ id));
        return new DiaryResponseDto(entity);
    }

//    public DiaryResponseDto update() {
//    }

    public void delete(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 없습니다. id=" + id));
        diaryRepository.delete(diary);
    }

    public List<DiaryResponseDto> findByMember(Long member_id) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + member_id));;
        List<Diary> diaries = diaryRepository.findByMember(member);
        return diaries.stream()
                .map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DiaryResponseDto> findAll() {
        return diaryRepository.findAll().stream()
                .map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }
}
