package com.team1.finalproject.sportsdata.service;

import com.team1.finalproject.common.service.DataParseBuilder;
import com.team1.finalproject.sportsdata.entity.Category;
import com.team1.finalproject.sportsdata.repository.CategoryRepository;
import com.team1.finalproject.sportsdata.repository.DataMemoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryBuildService {

    private final DataParseBuilder dataParseBuilder;
    private final CategoryRepository categoryRepository;
    private final DataMemoryRepository dataMemoryRepository;

    private void setSports() throws ParseException {
        String sportUrl = "https://sofasport.p.rapidapi.com/v1/sports";
        JSONArray resultArray = dataParseBuilder.getResponse(sportUrl);
        log.info("스포츠 데이터 세팅 시작...");
        for (Object o : resultArray) {
            JSONObject temp = (JSONObject) o;
            if (dataMemoryRepository.containsSports((String) temp.get("name"))){
                log.info((String) temp.get("name"));
                dataMemoryRepository.saveSports((Long) temp.get("id"), (String) temp.get("name"));
            }
        }
        log.info("스포츠 데이터 세팅 완료...");
    }

    private void setRegion() throws ParseException {
        log.info("지역 데이터 세팅 시작...");
        List<Long> sportsIdList = dataMemoryRepository.getSportsIdList();
        for(Long sportsId : sportsIdList){
            String url = "https://sofasport.p.rapidapi.com/v1/categories?sport_id=" + sportsId;
            JSONArray resultArray = dataParseBuilder.getResponse(url);
            if(resultArray!=null){
                for (Object o : resultArray) {
                    JSONObject temp = (JSONObject) o;
                    //미리 선택된 ID의 지역 정보만 받아옴
                    if (dataMemoryRepository.containsRegion((Long) temp.get("id"))){
                        log.info((String) temp.get("name"));
                        dataMemoryRepository.saveRegion((Long) temp.get("id"), (String) temp.get("name"), sportsId);
                    }
                }
            }
        }
        log.info("지역 데이터 세팅 완료...");
    }
    private void setLeague() throws ParseException {
        List<Long> regionIdList = dataMemoryRepository.getRegionIdList();
        log.info("리그 데이터 세팅 시작...");
        for (Long regionId : regionIdList) {
            String url = "https://sofasport.p.rapidapi.com/v1/unique-tournaments-alt?category_id="+regionId;
            JSONArray resultArray = (JSONArray)((JSONObject)dataParseBuilder.getResponse(url).get(0)).get("uniqueTournaments");
            if(resultArray!=null){
                for (Object o : resultArray) {
                    JSONObject temp = (JSONObject) o;
                    if (dataMemoryRepository.containsLeagues(dataParseBuilder.removeSpace((String) temp.get("name")))) {
                        log.info((String)temp.get("name"));
                        Long sportsId = dataMemoryRepository.getSportsIdByRegionId(regionId);
                        Category category = new Category(sportsId, dataMemoryRepository.getSportsName(sportsId),
                                regionId, dataMemoryRepository.getRegionName(regionId), (Long) temp.get("id"),
                                null, (String) temp.get("name"));
                        categoryRepository.save(category);
                    }
                }
            }
        }
        log.info("리그 데이터 세팅 완료...");
    }

    public String setCategory() throws ParseException {
        setSports();
        setRegion();
        setLeague();
        return "카테고리 세팅 완료";
    }
}
