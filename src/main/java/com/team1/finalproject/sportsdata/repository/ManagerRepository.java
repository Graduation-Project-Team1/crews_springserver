package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Manager;
import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByTeam(Team team);
}
