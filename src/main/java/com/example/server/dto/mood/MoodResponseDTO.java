package com.example.server.dto.mood;
import com.example.server.domain.entity.FamilyMood;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoodResponseDTO {
    private Long id;
    private List<String> tags;

    public static MoodResponseDTO fromEntity(FamilyMood mood) {
        List<String> tagNames = mood.getMoodTags().stream()
                .map(mt -> mt.getTag().getName())
                .collect(Collectors.toList());
        return MoodResponseDTO.builder()
                .id(mood.getId())
                .tags(tagNames)
                .build();
    }
}
