package com.example.server.domain.entity;

import com.example.server.domain.enums.ContentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class FamilyDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime writtenDate;

//    @Enumerated(EnumType.STRING)
//    private ContentType contentType;

    @OneToMany(mappedBy = "familyDiary", cascade = CascadeType.ALL)
    private List<DiaryParticipant> diaryParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "familyDiary", cascade = CascadeType.ALL)
    private List<DiaryTag> diaryTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;
}

