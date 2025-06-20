package com.example.server.repository;


import com.example.server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM User u WHERE u.status = 'INACTIVE' AND u.inactiveDateTime <= :threshold")
    void deleteInactiveUsers(@Param("threshold") LocalDateTime threshold);
}
