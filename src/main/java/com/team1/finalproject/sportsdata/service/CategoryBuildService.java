package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.repository.CategoryRepository;
import com.team1.finalproject.sportsdata.repository.DataMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryBuildService {

    private final DataParseBuilder dataParseBuilder;
    private final CategoryRepository categoryRepository;
    private final DataMemoryRepository dataMemoryRepository;

    private void setSports() throws ParseException {
        String sportUrl = "https://sofasport.p.rapidapi.com/v1/sports";
        JSONArray resultArray = dataParseBuilder.getResponse(sportUrl);
        for (Object o : resultArray) {
            JSONObject temp = (JSONObject) o;
            if (!dataMemoryRepository.containsSports((String) temp.get("name")))
                dataMemoryRepository.saveSports((Long) temp.get("id"), (String) temp.get("name"));
        }
    }

    private void setRegion() throws ParseException {
        List<Long> sportsIdList = dataMemoryRepository.getSportsIdList();
        for(Long sportsId : sportsIdList){
            String url = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=" + sportsId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object o : resultArray) {
                    JSONObject temp = (JSONObject) o;
                    //미리 선택된 ID의 지역 정보만 받아옴
                    if (dataMemoryRepository.containsRegion((Long) temp.get("id")))
                        dataMemoryRepository.saveRegion((Long) temp.get("id"), (String) temp.get("name"), sportsId);
                }
            }
        }
    }
    private void setLeague() throws ParseException {
        List<Long> regionIdList = dataMemoryRepository.getRegionIdList();
        for (Long regionId : regionIdList) {
            String url = "https://sofasport.p.rapidapi.com/v1/tournaments?category_id="+regionId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object o : resultArray) {
                    JSONObject temp = (JSONObject) o;
                    if (dataMemoryRepository.containsLeagues((String) temp.get("name"))) {
                        Long sportsId = dataMemoryRepository.getSportsIdByRegionId(regionId);
                        Category category = new Category(sportsId, dataMemoryRepository.getSportsName(sportsId),
                                regionId, dataMemoryRepository.getRegionName(regionId), (Long) temp.get("id"),
                                (Long) temp.get("uniqueId"), (String) temp.get("name"));
                        categoryRepository.save(category);
                    }
                }
            }
        }
    }

    public String setCategory() throws ParseException {
        setSports();
        setRegion();
        setLeague();
        return "Set Category completed";
    }
}
