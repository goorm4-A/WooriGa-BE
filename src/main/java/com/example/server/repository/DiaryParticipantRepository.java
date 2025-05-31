package com.example.server.repository;

import com.example.server.domian.entity.DiaryParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DiaryParticipantRepository extends JpaRepository<DiaryParticipant, Long> {
}
