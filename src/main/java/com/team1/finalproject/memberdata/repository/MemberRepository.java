package com.team1.finalproject.memberdata.repository;

import com.team1.finalproject.memberdata.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Boolean existsByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);
}
