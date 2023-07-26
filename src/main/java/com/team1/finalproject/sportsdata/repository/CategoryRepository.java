package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c.regionId from Category c")
    List<Long> findAllLeagueId();
    Boolean existsByLeagueId(Long leagueId);
    Category findByLeagueId(Long leagueId);

    @Query("select distinct c.regionId from Category c")
    List<Long> findAllRegionId();
    Category findByLeagueName(String s);
}
