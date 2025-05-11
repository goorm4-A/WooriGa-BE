package com.example.server.domian.entity;

import com.example.server.domian.enums.AnniversaryType;
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
@Table(name = "familyAnniversary")
public class FamilyAnniversary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private AnniversaryType anniversaryType;

    private LocalDateTime date;

    private Boolean alarmOn;

    @OneToMany(mappedBy = "familyAnniversary", cascade = CascadeType.ALL)
    private List<AnniversaryTag> anniversaryTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "familyMember_id")
    private FamilyMember familyMember;
}

