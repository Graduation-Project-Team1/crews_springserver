package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Optional<Season> findByCategory(Category category);
    List<Long> findSeasonTeamsById(Long seasonId);
}
