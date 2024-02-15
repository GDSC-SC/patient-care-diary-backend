package com.springboot.domain.category.service;

import com.springboot.domain.category.dto.CategoryRequestDto;
import com.springboot.domain.category.dto.CategoryResponseDto;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryCode;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.entity.Content;
import com.springboot.domain.content.entity.ContentRepository;
import com.springboot.domain.member.entity.Member;
import com.springboot.domain.member.entity.MemberRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.EntityNotFoundException;
import com.springboot.global.exception.InvalidInputException;
import com.springboot.security.jwt.dto.SecurityUserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ContentRepository contentRepository;
    @Transactional
    public long save(CategoryRequestDto requestDto) {
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));

        CategoryCode categoryCode = CategoryCode.findByCode(requestDto.getCategoryCode());
        if (categoryCode == null) {
            throw new InvalidInputException(ErrorCode.CATEGORY_CODE_NOT_FOUND, "해당 카테고리 코드가 없습니다. code=" + requestDto.getCategoryCode());
        }
        Category category = Category.builder()
                .member(member)
                .categoryCode(categoryCode)
                .subtitle(requestDto.getSubtitle())
                .color(requestDto.getColor())
                .build();
        return categoryRepository.save(category).getId();
    }

    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + id));
        return new CategoryResponseDto(category);
    }

    //업데이트 시 연관된 콘텐츠들의 카테고리 이름이 변경됨을 명시
    @Transactional
    public long update(CategoryRequestDto requestDto, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + id));
        CategoryCode categoryCode = CategoryCode.findByCode(requestDto.getCategoryCode());
        if (categoryCode == null) {
            throw new InvalidInputException(ErrorCode.CATEGORY_CODE_NOT_FOUND, "해당 카테고리 코드가 없습니다. code=" + requestDto.getCategoryCode());
        }
        category.update(categoryCode, requestDto.getSubtitle(), requestDto.getColor());
        return id;
    }

    //삭제 시 연관된 콘텐츠들도 삭제됨을 명시
    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + id));
        contentRepository.deleteAll(category.getContents());
        categoryRepository.delete(category);
    }

    //다이어리에 카테고리 안 뜨게 하고 싶을 때
    @Transactional
    public void setVisibility(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + id));
        category.setVisible(!category.getVisible());
    }

    public List<CategoryResponseDto> findMyCategory() {
        SecurityUserDto userDto = (SecurityUserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDto.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"잘못된 접근입니다. 해당 유저가 없습니다. email=" + email));
        return categoryRepository.findByMember(member).stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }


}
