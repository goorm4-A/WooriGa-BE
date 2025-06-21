package com.example.server.repository;

import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {

    Optional<FamilyMember> findByUserIdAndFamilyId(Long userId, Long familyId);

    Optional<FamilyMember> findByUserIdAndFamily(Long userId, Family family);

    List<FamilyMember> findByFamilyId(Long familyId);

<<<<<<< HEAD
    Optional<FamilyMember> findByUserId(Long userId);
=======
    List<FamilyMember> findAllByUser(User user);
>>>>>>> 4e353c315069ab0c53dfc25c7aa90bfba8d12b4a
}
