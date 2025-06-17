package com.example.server.repository.diary;

import com.example.server.domain.entity.DiaryParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryParticipantRepository extends JpaRepository<DiaryParticipant, Long> {
}
