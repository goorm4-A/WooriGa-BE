package com.example.server.dto.member;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FamilyGroupRequest {
    private String name;
    private MultipartFile image;
}
