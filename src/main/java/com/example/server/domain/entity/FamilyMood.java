package com.example.server.domain.entity;

import com.example.server.domain.enums.MoodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Setter
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
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;
}

