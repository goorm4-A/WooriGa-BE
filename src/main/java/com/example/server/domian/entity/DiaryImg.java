package com.example.server.domian.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="diaryImg")
@Data
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
