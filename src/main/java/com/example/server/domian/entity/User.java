package com.example.server.domian.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;
    private String phone;
    private String image;

    private String birthPlace;
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyMember> familyMembers = new ArrayList<>();
/*
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyMotto> familyMottos = new ArrayList<>();*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyEvent> familyEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyAnniversary> familyAnniversaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyRecipe> familyRecipes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DiaryParticipant> diaryParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();
}
