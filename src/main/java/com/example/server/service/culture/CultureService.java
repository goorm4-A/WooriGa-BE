package com.example.server.service.culture;

import com.example.server.dto.culture.CultureRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CultureService {

    public void createMotto(CultureRequestDTO.MottoRequestDTO mottoRequestDTO) {
        /*
        * TODO
        *  유저 사용자 조회
        *  가족 구성원 조회
        * */
        log.info("Create motto");
    }
}
