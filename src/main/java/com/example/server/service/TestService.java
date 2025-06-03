package com.example.server.service;

import com.example.server.dto.TestDTO;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public TestDTO getTestById(Integer id) {
        return new TestDTO(id, "테스트입니다.");
    }
}
