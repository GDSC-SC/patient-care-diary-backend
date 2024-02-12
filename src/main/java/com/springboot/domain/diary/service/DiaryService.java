package com.springboot.domain.diary.service;

import com.springboot.domain.diary.dto.DiaryListResponseDto;
import com.springboot.domain.diary.dto.DiaryRequestDto;
import com.springboot.domain.diary.dto.DiaryResponseDto;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.Emoji;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    public long save(DiaryRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"해당 유저가 없습니다. id=" + requestDto.getMemberId()));

        Diary diary = Diary.builder()
                .member(member)
                .date(requestDto.getDate())
                .build();
        return diaryRepository.save(diary).getId();
    }

    public DiaryResponseDto findById(Long id) {
        Diary entity = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + id));
        DiaryResponseDto responseDto = new DiaryResponseDto(entity);
        responseDto.setDiaryEmojis(countEmoji(entity));
        return responseDto;
    }

    public void delete(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + id));
        diaryRepository.delete(diary);
    }

    public List<DiaryListResponseDto> findByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"해당 유저가 없습니다. id=" + memberId));
        List<Diary> diaries = diaryRepository.findByMember(member);
        return diaries.stream()
                .map(diary -> {
                    DiaryListResponseDto dto = new DiaryListResponseDto(diary);
                    dto.setDiaryEmojis(countEmoji(diary));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<DiaryListResponseDto> findAll() {
        return diaryRepository.findAll().stream()
                .map(diary -> {
                    DiaryListResponseDto dto = new DiaryListResponseDto(diary);
                    dto.setDiaryEmojis(countEmoji(diary));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<DiaryEmojiResponseDto> countEmoji(Diary diary) {
        List<DiaryEmoji> diaryEmojis = diary.getDiaryEmojis();
        Map<Emoji, Integer> emojiCountMap = new HashMap<>();

        for (DiaryEmoji diaryEmoji : diaryEmojis) {
            Emoji emoji = diaryEmoji.getEmoji();
            System.out.println(emoji);
            emojiCountMap.put(emoji, emojiCountMap.getOrDefault(emoji, 0) + 1);
        }

        List<DiaryEmojiResponseDto> responseDtoList = new ArrayList<>();
        for (Map.Entry<Emoji, Integer> entry : emojiCountMap.entrySet()) {
            Emoji emoji = entry.getKey();
            Integer count = entry.getValue();
            DiaryEmojiResponseDto responseDto = DiaryEmojiResponseDto.builder()
                    .emoji(emoji)
                    .count(count)
                    .build();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }
}
