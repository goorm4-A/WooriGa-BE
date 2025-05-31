package com.example.server.domian.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer inviteCode;
    private String image;
    private String motto;
    private String mood;

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL)
    private List<FamilyMember> familyMembers = new ArrayList<>();

    //추가(민서)
    @OneToMany(mappedBy="family",cascade=CascadeType.ALL)
    private List<FamilyDiary> familyDiaries = new ArrayList<>();

}
