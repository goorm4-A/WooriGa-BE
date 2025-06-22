package com.example.server.domain.entity;

import com.example.server.domain.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String location;
    private String latitude;
    private String longitude;

    private String description;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private LocalDate timeline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;

    public void updateEvent(String title, String location, String latitude,
                            String longitude, LocalDate date) {
        this.title = title;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeline = date;
    }
}

