package com.example.server.domian.entity;

import com.example.server.domian.enums.MoodType;
import com.example.server.domian.enums.RuleType;
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
@Table(name = "familyMood")
public class FamilyMood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MoodType moodType;

    @OneToMany(mappedBy = "familyMood", cascade = CascadeType.ALL)
    private List<MoodTag> moodTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "familyMember_id")
    private FamilyMember familyMember;
}

