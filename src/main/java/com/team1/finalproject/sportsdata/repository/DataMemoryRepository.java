package com.team1.finalproject.sportsdata.repository;

import com.team1.finalproject.DataParseBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DataMemoryRepository {
    Map<Long, String> sports = new HashMap<>();
    Map<Long, String> regions = new HashMap<>();
    Map<Long, Long> regionSports = new HashMap<>();

    public boolean containsSports(String name) { // Enum 내부에 존재하는지 확인
        try {
            availableSports.valueOf(name);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public boolean containsRegion(Long id) { // Enum 내부에 존재하는지 확인
        for (availableRegions region : availableRegions.values()) {
            if (region.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean containsLeagues(String name) { // Enum 내부에 존재하는지 확인
        try {
            availableLeagues.valueOf(name);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public void saveSports(Long id, String name) {
        sports.put(id, name);
    }
    public ArrayList<Long> getSportsIdList() {
        return new ArrayList<>(sports.keySet());
    }
    public String getSportsName(Long id) {
        return sports.get(id);
    }
    public void saveRegion(Long id, String name, Long sportsId) {
        regions.put(id, name);
        regionSports.put(id, sportsId);
    }
    public ArrayList<Long> getRegionIdList() {
        return new ArrayList<>(regions.keySet());
    }

    public String getRegionName(Long id) {
        return regions.get(id);
    }

    public Long getSportsIdByRegionId(Long id) {
        return regionSports.get(id);
    }
}
