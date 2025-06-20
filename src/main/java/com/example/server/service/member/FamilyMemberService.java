package com.example.server.service.member;

import com.example.server.converter.FamilyConverter;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.member.FamilyGroupResponse;
import com.example.server.global.code.exception.CustomException;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 기능")
public class FamilyMemberService {

    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final S3Service s3Service;

    // 가족 그룹 생성
    public FamilyGroupResponse createFamilyGroup(User principalUser, String name, MultipartFile image) {
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
            code = (int) (Math.random() * 900000) + 100000; // 6자리
        } while (familyRepository.existsByInviteCode(code));
        return code;
    }
}
