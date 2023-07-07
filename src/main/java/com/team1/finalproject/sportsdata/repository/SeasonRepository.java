package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
}
