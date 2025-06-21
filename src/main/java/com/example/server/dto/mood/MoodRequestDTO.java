package com.example.server.dto.mood;

import com.example.server.domain.enums.MoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoodRequestDTO {
    private String tags; // comma separated string with optional '#'
    private MoodType moodType;
}
