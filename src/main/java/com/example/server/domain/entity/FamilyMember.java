package com.example.server.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relation;
    private String Image;

    private LocalDate memberBirthDate;  // 사용자 지정 생년월일
    private String memberName;          // 사용자 지정 이름
    private Boolean isUserAdded;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyMotto> familyMottos = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyEvent> familyEvents = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyAnniversary> familyAnniversaries = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyRecipe> familyRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyDiary> familyDiaries = new ArrayList<>();

    // 가족 그룹 생성 시
    public FamilyMember(User user, Family family, String relation) {
        this.user = user;
        this.family = family;
        this.relation = relation;
        this.isUserAdded = false;
    }

    // 가족 구성원 생성 시 (= 사용자 지정, isUserAdded = true)
    public FamilyMember(Family family, String memberName,
                        LocalDate memberBirthDate, String relation, String image) {
        this.user = null;
        this.family = family;
        this.memberName = memberName;
        this.memberBirthDate = memberBirthDate;
        this.relation = relation;
        this.Image = image;
        this.isUserAdded = true;
    }
}
