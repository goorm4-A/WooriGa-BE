package com.example.server.utils.jwt;

import com.example.server.domain.entity.User;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/oauth")
                || requestURI.startsWith("/swagger-ui")
                || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 헤더에서 토큰 추출 후 유저 조회
            String token = jwtManager.getToken(request);
            if (token != null) {
                Long userId = jwtManager.validateAccessToken(token);

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException(ErrorStatus.JWT_UNKNOWN_ERROR); // 그외 예외
        }

        filterChain.doFilter(request, response);
    }
}
