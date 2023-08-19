package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByName(String playerName);

    List<Player> findByTeamId(Long teamId);
}
