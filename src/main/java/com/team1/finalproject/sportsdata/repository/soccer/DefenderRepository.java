package com.team1.finalproject.sportsdata.repository.soccer;

import com.team1.finalproject.sportsdata.entity.soccer.Defender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefenderRepository extends JpaRepository<Defender, Long> {

}
