package com.example.server.domain.entity;

import com.example.server.domain.enums.FromType;

import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "familyDiary")
public class FamilyDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime writtenDate;

    //업로드할 사진
    @OneToMany(mappedBy="familyDiary",cascade=CascadeType.ALL)
    private List<DiaryImg> images=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private FromType contentType;


    @OneToMany(mappedBy = "familyDiary", cascade = CascadeType.ALL)
    private List<DiaryParticipant> diaryParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "familyDiary", cascade = CascadeType.ALL)
    private List<DiaryTag> diaryTags = new ArrayList<>();

    //일기 작성자
    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;

    @ManyToOne
    @JoinColumn(name="family_id")
    private Family family;



    public FamilyDiary(String title, List<DiaryTag> diaryTags, List<DiaryParticipant> diaryParticipants, String location, String description, FromType contentType) {
        this.title=title;
        this.diaryTags = diaryTags;
        this.diaryParticipants = diaryParticipants;
        this.location = location;
        this.description = description;
        this.contentType = contentType;
    }

    public FamilyDiary(String title, String location, String description, FromType contentType) {
        this.title=title;
        this.location = location;
        this.description = description;
        this.contentType = contentType;
    }

    public static FamilyDiaryResponseDto toDto(FamilyDiary diary) {
        List<Long> participantIds = diary.getDiaryParticipants().stream()
                .map(DiaryParticipant::getId)
                .collect(Collectors.toList());

        return new FamilyDiaryResponseDto(
                diary.getId(),
                diary.getTitle(),
                diary.getLocation(),
                diary.getDescription(),
                diary.getContentType(),
                participantIds
        );
    }


}

