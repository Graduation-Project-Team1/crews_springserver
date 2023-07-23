package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Long> findAllLeagueId();
    Boolean existsByLeagueId(Long leagueId);
    Category findByLeagueId(Long leagueId);

    List<Long> findAllRegionId();

    Category findByLeagueName(String s);
}
