package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="diaryImg")
@Getter
@Setter
@NoArgsConstructor
public class DiaryImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=true)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name="familyDiary_id",nullable = false)
    private FamilyDiary familyDiary;

    public DiaryImg(String imgUrl, FamilyDiary familyDiary) {
        this.imgUrl = imgUrl;
        this.familyDiary = familyDiary;
    }
}
