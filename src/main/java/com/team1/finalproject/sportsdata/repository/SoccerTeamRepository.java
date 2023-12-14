package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.SoccerTeam;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoccerTeamRepository extends JpaRepository<SoccerTeam, Long> {

}
