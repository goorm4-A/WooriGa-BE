package com.example.server.service.user;

import com.example.server.converter.UserConverter;
import com.example.server.domain.entity.User;
import com.example.server.dto.user.UserInfoResponse;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(User PrincipalUser) {
        User user = userRepository.findById(PrincipalUser.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));
        return UserConverter.toUserInfoResponse(user);
    }
}
