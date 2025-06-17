package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DiaryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "family_diary_id")
    private FamilyDiary familyDiary;
}
