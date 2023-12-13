package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("select exists(select 1 from Player p where p.code = :code and p.name = :name)")
    boolean existsByName(@Param("code")String code, @Param("name") String playerName);

    List<Player> findAllByTeamId(Long teamId);

    /*@Query("select distinct p from Player p where p.code = :code and p.team = :team")
    List<Player> findByTeamId(@Param("code")String code, @Param("team") Team team);*/

    List<Player> searchTop5ByNameContainingIgnoreCase(String query);
}
