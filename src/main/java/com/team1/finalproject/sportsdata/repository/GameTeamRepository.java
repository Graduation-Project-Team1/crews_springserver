package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Game;
import com.team1.finalproject.sportsdata.entity.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTeamRepository extends JpaRepository<GameTeam, Long> {
    boolean existsByEvent(Game event);
}
