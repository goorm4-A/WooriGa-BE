package com.example.server.domian.entity;

import com.example.server.domian.enums.ContentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diaryParticipant")
public class DiaryParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String location;

    private LocalDateTime writtenDate;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "familyDiary_id")
    private FamilyDiary familyDiary;
}

