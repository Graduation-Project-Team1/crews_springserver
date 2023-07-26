package com.team1.finalproject.memberdata.repository;

import com.team1.finalproject.memberdata.entity.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    Boolean existsByNickname(String nickname);
}
