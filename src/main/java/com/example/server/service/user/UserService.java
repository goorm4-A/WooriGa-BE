package com.example.server.service.user;

import com.example.server.converter.UserConverter;
import com.example.server.domain.entity.User;
import com.example.server.domain.enums.UserStatus;
import com.example.server.dto.user.UserInfoRequest;
import com.example.server.dto.user.UserInfoResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 정보 조회
    public UserInfoResponse getUserInfo(User PrincipalUser) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));
        return UserConverter.toUserInfoResponse(user);
    }

    // 유저 정보 수정
    @Transactional
    public UserInfoResponse updateUserInfo(User PrincipalUser, UserInfoRequest infoRequest) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        user.updateInfo(infoRequest.getName(), infoRequest.getPhone(), infoRequest.getBirthDate());
        return UserConverter.toUserInfoResponse(user);
    }

    // 유저 상태 수정
    @Transactional
    public String updateUserStatus(User PrincipalUser) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        if (user.getStatus() == UserStatus.ACTIVE) {
            // 활성 > 비활성
            user.setInactiveStatus();
            return "변경된 status 상태: " + user.getStatus() + "(30일 후 유저 삭제)";
        } else {
            // 비활성 > 활성
            user.setActiveStatus();
            return "변경된 status 상태: " + user.getStatus();
        }
    }

    // 유저 삭제
    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 삭제
    @Transactional
    public void deleteInactiveUsers() {
        LocalDateTime now = LocalDateTime.now();
        userRepository.deleteInactiveUsers(now.minusDays(30)); // 30일 기준
    }
}
