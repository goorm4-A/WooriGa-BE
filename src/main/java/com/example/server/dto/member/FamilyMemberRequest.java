package com.example.server.dto.member;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Getter
public class FamilyMemberRequest {
    private String name;
    private String relation;
    private LocalDate birthDate;
}
