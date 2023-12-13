package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("select distinct t from Team t where t.name = :name and t.code = :code")
    Team findByName(@Param("name") String name, @Param("code") String code);

    List<Team> searchAllByNameContainingIgnoreCase(@Param("query") String query);
}
