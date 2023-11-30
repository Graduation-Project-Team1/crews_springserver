package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    /*@Query("select distinct s from Season s where s.code = :code and s.category = :category")
    Optional<Season> findByCategory(@Param("category")Category category, @Param("code") String code);*/
    List<Long> findSeasonTeamsById(Long seasonId);

    Optional<Season> findByCategory(Category category);
}
