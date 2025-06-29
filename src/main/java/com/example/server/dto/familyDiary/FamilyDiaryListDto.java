package com.example.server.dto.familyDiary;

import com.example.server.domain.entity.DiaryImg;
import com.example.server.domain.entity.FamilyDiary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDiaryListDto {

    //유저 닉네임
    private String username;
    //유저 프로필
    private String profile;

    private Long id;
    private String imgUrl;
    private String title;

    public static List<FamilyDiaryListDto> toDto(List<FamilyDiary> list) {
        return list.stream()
                .map(familydiary -> {
                    String firstImgUrl = familydiary.getImages().isEmpty()
                            ? null
                            : familydiary.getImages().get(0).getImgUrl();
                    return new FamilyDiaryListDto(
                            familydiary.getFamilyMember().getUser().getName(),
                            familydiary.getFamilyMember().getUser().getImage(),
                            familydiary.getId(),
                            firstImgUrl,
                            familydiary.getTitle()
                    );
                })
                .collect(Collectors.toList());

    }
}
