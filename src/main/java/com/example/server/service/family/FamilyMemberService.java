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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public FamilyMemberDetailDTO getFamilyMemberDetail(User principalUser, Long groupId, Long memberId) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신의 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID));

        FamilyMember familyMember = familyMemberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        // 구성원의 가족 그룹이 맞는지 확인
        if (!familyMember.getFamily().getId().equals(groupId)) {
            throw new CustomException(ErrorStatus.FAMILY_MEMBER_INVALID);
        }

        return FamilyConverter.toFamilyMemberDetailDTO(familyMember);
    }
}
