package com.springboot.domain.content.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.springboot.domain.category.entity.Category;
import com.springboot.domain.category.entity.CategoryRepository;
import com.springboot.domain.content.dto.ContentRequestDto;
import com.springboot.domain.content.dto.ContentResponseDto;
import com.springboot.domain.content.dto.ContentUpdateRequestDto;
import com.springboot.domain.content.entity.Content;
import com.springboot.domain.content.entity.ContentRepository;
import com.springboot.domain.diary.entity.Diary;
import com.springboot.domain.diary.entity.DiaryRepository;
import com.springboot.global.error.ErrorCode;
import com.springboot.global.exception.DuplicateRequestException;
import com.springboot.global.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final DiaryRepository diaryRepository;
    private final CategoryRepository categoryRepository;
    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
    private String bucketName;
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;

    @Transactional
    public long save(ContentRequestDto requestDto) {
        Diary diary = diaryRepository.findById(requestDto.getDiaryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + requestDto.getDiaryId()));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CATEGORY_NOT_FOUND, "해당 카테고리가 없습니다. id=" + requestDto.getCategoryId()));

        if (contentRepository.findContentByDiaryAndCategory(diary, category).isPresent()) {
            throw new DuplicateRequestException(ErrorCode.CONTENT_DUPLICATE_REQUEST, "해당 다이어리 id=" + requestDto.getDiaryId() + " 해당 카테고리 id=" + requestDto.getCategoryId() + "의 콘텐츠는 중복된 요청입니다.");
        }

        Content content = Content.builder()
                .diary(diary)
                .category(category)
                .done(requestDto.getDone())
                .text(requestDto.getText())
                .build();
        return contentRepository.save(content).getId();
    }

    public ContentResponseDto findById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        return new ContentResponseDto(content);
    }

    @Transactional
    public long update(ContentUpdateRequestDto requestDto, Long id) throws IOException {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        //String photoUrl = uploadImage(requestDto.getImage());
        content.update(requestDto.getDone(), content.getPhotoUrl(), requestDto.getText());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.CONTENT_NOT_FOUND, "해당 컨텐츠가 없습니다. id=" + id));
        contentRepository.delete(content);
    }

    //content response dto category 수정해야할수도
    public List<ContentResponseDto> findByDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DIARY_NOT_FOUND, "해당 다이어리가 없습니다. id=" + diaryId));
        return diary.getContents().stream()
                .map(ContentResponseDto::new)
                .collect(Collectors.toList());
    }

    public String uploadImage(MultipartFile image) throws IOException {
        InputStream keyFile = ResourceUtils.getURL(keyFileName).openStream();

        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = image.getContentType(); // 파일의 형식 ex) JPG

        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(keyFile))
                .build()
                .getService();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        if (image.isEmpty()) {
            imgUrl = null;
        } else {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                    .setContentType(ext).build();
            Blob blob = storage.create(blobInfo, image.getBytes());
        }
        return imgUrl;
    }
}
