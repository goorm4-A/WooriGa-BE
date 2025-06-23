package com.example.server.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MainResponse {
    private String userName;
    private String userImage;
    private List<String> familyNames;
    private String latestFamilyImage;
    private List<String> todayImages;
}
