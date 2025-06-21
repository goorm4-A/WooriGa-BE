package com.example.server.domain.entity;

import com.example.server.domain.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String name;

    private String nickname;
    private String password;
    private String phone;
    private String image;

    private String birthPlace;
    private LocalDate birthDate;

    private String accessToken;
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    private LocalDateTime inactiveDateTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyMember> familyMembers = new ArrayList<>();
/*
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyMotto> familyMottos = new ArrayList<>();*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyEvent> familyEvents = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyAnniversary> familyAnniversaries = new ArrayList<>();

/*
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FamilyRecipe> familyRecipes = new ArrayList<>();
*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<DiaryParticipant> diaryParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Alarm> alarms = new ArrayList<>();

    public User(String email, String nickname, String image) {
        this.email = email;
        this.name = nickname; // 카카오 닉네임 > 우리가 이름
        this.image = image;

        status = UserStatus.ACTIVE;
    }

    public void setTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void updateInfo(String name, String phone, LocalDate birthDate) {
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    // 활성 > 비활성
    public void setInactiveStatus() {
        this.status = UserStatus.INACTIVE;
        this.inactiveDateTime = LocalDateTime.now();
    }

    // 비활성 > 활성
    public void setActiveStatus() {
        this.status = UserStatus.ACTIVE;
        this.inactiveDateTime = null;
    }
}
