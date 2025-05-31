package com.example.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.server.domain.entity.DiaryImg;

@Repository
public interface DiaryImgRepository extends JpaRepository<DiaryImg, Long> {
}
