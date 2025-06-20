package com.example.server.domain.entity;

import com.example.server.domain.enums.AnniversaryType;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyAnniversary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;
    
    //태그
    @Enumerated(EnumType.STRING)
    private AnniversaryType anniversaryType;

    private LocalDate date; //기념일 날짜

    private String location;

    //✏️추후 기념일 전날에 푸시 알림 보내는 방식으로 추가
//    private Boolean alarmOn;

//    @OneToMany(mappedBy = "familyAnniversary", cascade = CascadeType.ALL)
//    private List<AnniversaryTag> anniversaryTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="family_id")
    private Family family;

    @ManyToOne
    @JoinColumn(name = "family_member_id")
    private FamilyMember familyMember;


}

