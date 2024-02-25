package com.springboot.domain.diaryemoji.service;

import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiRequestDto;
import com.springboot.domain.diaryemoji.dto.DiaryEmojiResponseDto;
import com.springboot.domain.diaryemoji.entity.DiaryEmoji;
import com.springboot.domain.diaryemoji.entity.DiaryEmojiRepository;
import com.springboot.domain.diaryemoji.entity.Emoji;
import com.springboot.domain.diaryemoji.entity.EmojiCount;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.DuplicateRequestException;
import com.springboot.global.exception.EntityNotFoundException;
import com.springboot.global.exception.InvalidInputException;
import com.springboot.security.jwt.dto.SecurityUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DiaryEmojiService {

    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryEmojiRepository diaryEmojiRepository;

    @Transactional
    public long update(DiaryEmojiRequestDto requestDto) {

        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));

        Diary diary = diaryRepository.findById(requestDto.getDiaryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + requestDto.getDiaryId()));

        Emoji emoji = Emoji.findByCode(requestDto.getEmojiCode());
        if (emoji == null) {
            throw new InvalidInputException(ErrorCode.EMOJI_CODE_NOT_FOUND, "해당 이모지 코드가 없습니다. code=" + requestDto.getEmojiCode());
        }
        DiaryEmoji diaryEmoji = diaryEmojiRepository.findByMemberAndDiary(member, diary).orElse(null);
        if (diaryEmoji == null) {
            diaryEmoji = DiaryEmoji.builder()
                    .member(member)
                    .diary(diary)
                    .emoji(emoji)
                    .build();
            diaryEmojiRepository.save(diaryEmoji);
        } else {
            diaryEmoji.setEmoji(emoji);
        }
        return diaryEmoji.getId();
    }
    @Transactional
    public void delete(Long diaryId, String emojiCode) {

        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + diaryId));

        DiaryEmoji diaryEmoji = diaryEmojiRepository.findByMemberAndDiary(member, diary)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMOJI_NOT_FOUND, "사용자 email " + email + "가 해당 다이어리 " + diaryId + "에 누른 이모지가 없습니다."));

        if (!diaryEmoji.getEmoji().getCode().equals(emojiCode)) {
            throw new InvalidInputException(ErrorCode.EMOJI_CODE_NOT_FOUND, "사용자 email " + email + "가 해당 이모지 code=" + emojiCode + "를 누른 적이 없습니다.");
        }
        diaryEmojiRepository.delete(diaryEmoji);
    }

    public DiaryEmojiResponseDto findByDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + diaryId));
        List<DiaryEmoji> diaryEmojis = diary.getDiaryEmojis();
        Map<Emoji, Integer> emojiCountMap = new HashMap<>();

        for (DiaryEmoji diaryEmoji : diaryEmojis) {
            Emoji emoji = diaryEmoji.getEmoji();
            System.out.println(emoji);
            emojiCountMap.put(emoji, emojiCountMap.getOrDefault(emoji, 0) + 1);
        }

        List<EmojiCount> emojiCounts = new ArrayList<>();
        for (Map.Entry<Emoji, Integer> entry : emojiCountMap.entrySet()) {
            Emoji emoji = entry.getKey();
            Integer count = entry.getValue();
            EmojiCount emojiCount = EmojiCount.builder()
                    .emoji(emoji)
                    .count(count)
                    .build();
            emojiCounts.add(emojiCount);
        }

        return new DiaryEmojiResponseDto(emojiCounts, findMyEmojiState(diary));

    }
    private Emoji findMyEmojiState(Diary diary) {
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));

        Optional<DiaryEmoji> diaryEmojiOptional = diaryEmojiRepository.findByMemberAndDiary(member, diary);
        return diaryEmojiOptional.map(DiaryEmoji::getEmoji).orElse(Emoji.NONE);
    }
}
