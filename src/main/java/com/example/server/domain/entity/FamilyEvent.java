package com.example.server.domain.entity;

import com.example.server.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FamilyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String description;

//    @Enumerated(EnumType.STRING)
//    private EventType eventType;

    private LocalDateTime timeline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;
}

