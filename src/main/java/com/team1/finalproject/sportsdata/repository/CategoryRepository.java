package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.sportsdata.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
