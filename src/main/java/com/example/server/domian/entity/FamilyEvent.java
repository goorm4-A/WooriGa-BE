package com.example.server.domian.entity;

import com.example.server.domian.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "familyEvent")
public class FamilyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;

    private String description;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDateTime timeline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "familyMember_id")
    private FamilyMember familyMember;
}

