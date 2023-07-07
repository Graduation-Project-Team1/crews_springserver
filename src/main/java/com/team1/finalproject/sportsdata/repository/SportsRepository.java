package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Sports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {
}
