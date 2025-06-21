package com.example.server.service.family;

import com.example.server.converter.FamilyConverter;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.member.*;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.code.exception.handler.FamilyMemberHandler;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.UserRepository;
import com.example.server.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FamilyMemberService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final S3Service s3Service;

    // 가족 구성원 생성
    public FamilyMemberResponse createFamilyMember(User principalUser, Long groupId,
                                                   String name, String relation,
                                                   LocalDate birthDate, MultipartFile image) {

        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신의 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_INVALID));

        // 이미지 업로드
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = s3Service.uploadImage(image, "family-members");
            } catch (IOException e) {
                throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }

        // 사용자 지정 가족 구성원 생성
        FamilyMember newFamilyMember = familyMemberRepository.save(new FamilyMember(family, name, birthDate, relation, imageUrl));

        return FamilyConverter.toFamilyMemberResponse(newFamilyMember);
    }

    // 가족 구성원 상세 조회
    public FamilyMemberDetailResponse getFamilyMemberDetail(User principalUser, Long groupId, Long memberId) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신의 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID));

        // 존재하는 가족 구성원인지 확인
        FamilyMember familyMember = familyMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        // 구성원의 가족 그룹이 맞는지 확인
        if (!familyMember.getFamily().getId().equals(groupId)) {
            throw new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID);
        }

        return FamilyConverter.toFamilyMemberDetailResponse(familyMember);
    }

    // 가족 구성원 수정
    @Transactional
    public FamilyMemberDetailResponse updateFamilyMember(User principalUser, Long groupId, Long memberId,
                                                         String name, String relation, LocalDate birthDate, MultipartFile image) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신의 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID));

        // 존재하는 가족 구성원인지 확인
        FamilyMember familyMember = familyMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        // 구성원의 가족 그룹이 맞는지 확인
        if (!familyMember.getFamily().getId().equals(groupId)) {
            throw new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID);
        }

        // 기존 이미지 삭제 + 새 이미지 업로드
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            // 기존 이미지가 있다면 삭제
            String existingImageUrl = familyMember.getImage();
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {

                // 버킷, 확장자 제외한 파일명만 추출
                String filename = existingImageUrl.substring(existingImageUrl.indexOf(".com/") + 5);
                s3Service.deleteFile(filename);
            }

            // 새 이미지 업로드
            try {
                imageUrl = s3Service.uploadImage(image, "family-members");
            } catch (IOException e) {
                throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }

        familyMember.updateFamilyMember(name, relation, birthDate, imageUrl);
        return FamilyConverter.toFamilyMemberDetailResponse(familyMember);
    }

    // 가족 구성원 삭제
    @Transactional
    public String deleteFamilyMember(User principalUser, Long groupId, Long memberId) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신의 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID));

        // 존재하는 가족 구성원인지 확인
        FamilyMember familyMember = familyMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        // 구성원의 가족 그룹이 맞는지 확인
        if (!familyMember.getFamily().getId().equals(groupId)) {
            throw new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID);
        }

        familyMemberRepository.delete(familyMember);
        return "해당 가족 구성원이 삭제되었습니다.";
    }
}
