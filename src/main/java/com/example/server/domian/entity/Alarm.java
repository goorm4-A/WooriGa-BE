package com.example.server.domian.entity;

import com.example.server.domian.enums.AlarmType;
import com.example.server.domian.enums.FromType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    private FromType fromType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}




