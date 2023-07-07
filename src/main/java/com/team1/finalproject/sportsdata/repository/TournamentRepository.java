package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Tournament findByName(String s);
}
