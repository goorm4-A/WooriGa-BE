package com.example.server.config;

import com.example.server.repository.UserRepository;
import com.example.server.utils.jwt.JwtAuthenticationFilter;
import com.example.server.utils.jwt.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtManager jwtManager;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**").permitAll()   // 비회원 허용 가능
                        .anyRequest().authenticated()              // 그 외 인증 필요
                )
                .csrf(csrf -> csrf.disable()) // CSRF 설정
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtManager, userRepository),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
