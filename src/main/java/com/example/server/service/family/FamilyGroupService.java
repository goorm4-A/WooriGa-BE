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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyGroupService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final S3Service s3Service;

    // 가족 그룹 생성
    public FamilyResponse createFamilyGroup(User principalUser, String name, MultipartFile image) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        // 초대코드 생성
        int inviteCode = createInviteCode();

        // 이미지 업로드
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = s3Service.uploadImage(image, "family-groups");
            } catch (IOException e) {
                throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }

        // 가족 그룹 생성
        Family newFamily = familyRepository.save(new Family(name, inviteCode, imageUrl));

        // 가족 멤버 생성(= 나 포함 기본 6명)
        List<String> defaultRelations = List.of(
                "나", "엄마", "아빠", "할머니", "할아버지", "외할머니", "외할아버지");

        List<FamilyMember> members = new ArrayList<>();

        for (int i = 0; i < defaultRelations.size(); i++) {
            String relation = defaultRelations.get(i);
            User relatedUser = (i == 0) ? user : null;
            FamilyMember member = new FamilyMember(relatedUser, newFamily, relation);
            members.add(member);
        }
        familyMemberRepository.saveAll(members);

        return FamilyConverter.toFamilyGroupResponse(newFamily);
    }

    public int createInviteCode() {
        int code;
        do {
            code = (int) (Math.random() * 900000) + 100000; // 6자리 (100000 ~ 999999)
        } while (familyRepository.existsByInviteCode(code));
        return code;
    }

    // 가족 그룹 조회
    public List<FamilyGroupResponse> getFamilyGroup(User principalUser) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        List<FamilyMember> familyMembers = familyMemberRepository.findAllByUser(user);

        List<Family> families = familyMembers.stream()
                .map(FamilyMember::getFamily)
                .distinct() // 중복 제거
                .toList();

        return FamilyConverter.toFamilyGroupListResponse(families);
    }

    // 가족 그룹 상세 조회 (가계도)
    public FamilyGroupDetailResponse getFamilyGroupDetail(User principalUser, Long groupId) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신이 속한 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_INVALID));


        List<FamilyMember> familyMembers = family.getFamilyMembers();

        return FamilyConverter.toFamilyGroupDetailResponse(familyMembers);
    }

    // 가족 그룹 수정
    @Transactional
    public FamilyResponse updateFamilyGroup(User principalUser, Long groupId, String name, MultipartFile image) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신이 속한 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_INVALID));

        // 기존 이미지 삭제 + 새 이미지 업로드
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            // 기존 이미지가 있다면 삭제
            String existingImageUrl = family.getImage();
            if (existingImageUrl != null && !existingImageUrl.isEmpty()) {

                // 버킷, 확장자 제외한 파일명만 추출
                String filename = existingImageUrl.substring(existingImageUrl.indexOf(".com/") + 5);
                s3Service.deleteFile(filename);
            }

            // 새 이미지 업로드
            try {
                imageUrl = s3Service.uploadImage(image, "family-groups");
            } catch (IOException e) {
                throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }

        family.updateFamilyGroup(name, imageUrl);
        return FamilyConverter.toFamilyGroupResponse(family);
    }

    // 가족 그룹 삭제
    @Transactional
    public String deleteFamilyGroup(User principalUser, Long groupId) {
        User user = userRepository.findById(principalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        Family family = familyRepository.findById(groupId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        // 자신이 속한 가족 그룹이 맞는지 확인
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new FamilyMemberHandler(ErrorStatus.FAMILY_MEMBER_INVALID));

        familyRepository.delete(family);
        return "해당 가족 그룹이 삭제되었습니다.";
    }
}
