package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select distinct c.sportsName from Category c")
    List<String> findAllSportsName();
    @Query("select distinct c.leagueId from Category c")
    List<Long> findAllLeagueId();
    List<Category> findBySportsName(String sportsName);
    Boolean existsByLeagueId(Long leagueId);
    Optional<Category> findByLeagueId(Long leagueId);

    @Query("select distinct c.regionId from Category c")
    List<Long> findAllRegionId();
    Optional<Category> findByLeagueName(String s);

    Optional<Category> findBySportsId(Long sportsId);
    @Query("select distinct c.sportsName from Category c where c.sportsId = :sportsId")
    String findSportsNameBySportsId(@Param("sportsId") Long sportsId);
}
