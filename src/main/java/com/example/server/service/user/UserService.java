package com.example.server.service.user;

import com.example.server.converter.UserConverter;
import com.example.server.domain.entity.User;
import com.example.server.dto.user.UserInfoRequest;
import com.example.server.dto.user.UserInfoResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Tag(name = "User", description = "유저 관련 기능")
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(User PrincipalUser) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));
        return UserConverter.toUserInfoResponse(user);
    }

    @Transactional
    public UserInfoResponse updateUserInfo(User PrincipalUser, UserInfoRequest infoRequest) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

        user.updateInfo(infoRequest.getName(), infoRequest.getPhone(), infoRequest.getBirthDateTime());
        return UserConverter.toUserInfoResponse(user);
    }
}
