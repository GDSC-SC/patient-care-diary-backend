package com.springboot.domain.diaryemoji.service;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiRequestDto;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.DiaryEmojiRepository;
import com.springboot.domain.diaryemoji.entity.Emoji;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryEmojiService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryEmojiRepository diaryEmojiRepository;
    public long save(DiaryEmojiRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + requestDto.getMemberId()));
        Diary diary = diaryRepository.findById(requestDto.getDiaryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 없습니다. id=" + requestDto.getDiaryId()));

        Emoji emoji = Emoji.findByCode(requestDto.getEmojiCode());
        if (emoji == null) {
            throw (new IllegalArgumentException("해당 이모지 코드가 없습니다. code=" + requestDto.getEmojiCode()));
        }

        if (diaryEmojiRepository.findByMemberAndDiary(member, diary).isPresent()){
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        DiaryEmoji diaryEmoji = DiaryEmoji.builder()
                .member(member)
                .diary(diary)
                .emoji(emoji)
                .build();
        return diaryEmojiRepository.save(diaryEmoji).getId();
    }
    public void delete(Long memberId, Long diaryId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + memberId));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리가 없습니다. id=" + diaryId));

        DiaryEmoji diaryEmoji = diaryEmojiRepository.findByMemberAndDiary(member, diary)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이어리 이모지가 없습니다. memberId=" + memberId + ", diaryId=" + diaryId));
        diaryEmojiRepository.delete(diaryEmoji);
    }
}
