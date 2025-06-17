package com.example.server.repository;


import com.example.server.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Boolean existsByName(String name);
    Optional<com.example.server.domain.entity.Tag> findByName(String name);
}
