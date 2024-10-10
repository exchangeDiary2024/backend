package com.exchangediary.diary.service;

import com.exchangediary.diary.domain.DiaryRepository;
import com.exchangediary.diary.domain.entity.Diary;
import com.exchangediary.diary.domain.entity.UploadImage;
import com.exchangediary.diary.ui.dto.request.DiaryRequest;
import com.exchangediary.global.exception.ErrorCode;
import com.exchangediary.global.exception.serviceexception.DuplicateException;
import com.exchangediary.global.exception.serviceexception.FailedImageUploadException;
import com.exchangediary.group.domain.GroupRepository;
import com.exchangediary.group.domain.entity.Group;
import com.exchangediary.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryCommandService {
    private final DiaryRepository diaryRepository;
    private final GroupRepository groupRepository;

    public Long createDiary(DiaryRequest diaryRequest, MultipartFile file, Long groupId, Member member) {
        checkTodayDiaryExistent();

        if (isEmptyFile(file)) {
            Diary diary = Diary.of(diaryRequest, null);
            Diary savedDiary = diaryRepository.save(diary);
            return savedDiary.getId();
        }

        try {
            UploadImage image = UploadImage.builder()
                    .image(file.getBytes())
                    .build();
            Diary diary = Diary.of(diaryRequest, image);
            Group group = groupRepository.findById(groupId).get();
            diary.addMemberAndGroup(member, group);
            Diary savedDiary = diaryRepository.save(diary);
            image.uploadToDiary(savedDiary);
            return savedDiary.getId();
        } catch (IOException e) {
            throw new FailedImageUploadException(
                    ErrorCode.FAILED_UPLOAD_IMAGE,
                    "",
                    file.getOriginalFilename()
            );
        }
    }

    private void checkTodayDiaryExistent() {
        LocalDate today = LocalDate.now();
        Optional<Long> todayDiary = diaryRepository.findIdByDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth());

        if (todayDiary.isPresent()) {
            throw new DuplicateException(
                    ErrorCode.DIARY_DUPLICATED,
                    "",
                    today.toString()
            );
        }
    }

    private boolean isEmptyFile(MultipartFile file) {
        return file == null || file.isEmpty();
    }

}
