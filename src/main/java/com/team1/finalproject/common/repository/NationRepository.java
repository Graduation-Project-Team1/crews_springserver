package com.team1.finalproject.common.repository;

import com.team1.finalproject.common.entity.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {
    Nation findByName(String name);
}
