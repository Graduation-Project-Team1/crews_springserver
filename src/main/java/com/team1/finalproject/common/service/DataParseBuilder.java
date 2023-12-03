package com.team1.finalproject.common.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataParseBuilder {

    @Value("${SofaSport_Key_2}")
    private String SofaSport_API_Key;
    public JSONArray getResponse(String url) throws ParseException, HttpClientErrorException.NotFound {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", SofaSport_API_Key);
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("API 요청 중에 예외가 발생했습니다.");
            System.out.println(ex.getMessage());
            return null;
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        JSONArray resultArray;
        if (url.contains("/unique-tournaments/seasons?")) {
            resultArray = (JSONArray) jsonObject.get("data");
        } /*else if (url.contains("/seasons/standings")) {
            resultArray = (JSONArray) ((JSONObject) ((JSONObject) jsonObject.get("data")).get(0)).get("rows");
        }*/ else if (url.contains("/teams/players")) {
            resultArray = (JSONArray) ((JSONObject) jsonObject.get("data")).get("players");
        } else if (url.contains("/seasons/teams-statistics")) {
            resultArray = (JSONArray) ((JSONObject)jsonObject.get("data")).get("avgRating");
        } else
            resultArray = (JSONArray) jsonObject.get("data");

        return resultArray;
    }

    public JSONObject getJSONObject(String url) throws ParseException {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", SofaSport_API_Key);
        headers.set("X-RapidAPI-Host", "sofasport.p.rapidapi.com");
        String requestBody = "";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("API 요청 중에 예외가 발생했습니다.");
            System.out.println(ex.getMessage());
            return null;
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        return (JSONObject) jsonObject.get("data");
    }
    public Timestamp toTimeStamp(long num){
        return new Timestamp(num * 1000);
    }

    public String nameTrimmer(String name){
        String result1 = name.trim().replaceAll("-", "");
        int frontLength = result1.indexOf(' ');
        int backLength = result1.length() - result1.lastIndexOf(' ') - 1;

        if(frontLength < backLength)
            return result1;
        else {
            String[] parts = result1.split("\\s+");
            return parts[1] + " " +parts[0];
        }
    }
    public int calculateAge(Timestamp timestamp){
        Date birthDate = new Date(timestamp.getTime());
        Date currentDate = new Date();
        java.util.Calendar birthCalendar = java.util.Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        java.util.Calendar currentCalendar = java.util.Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int age = currentCalendar.get(java.util.Calendar.YEAR) - birthCalendar.get(java.util.Calendar.YEAR);

        // 생일이 지났는지 체크
        if (currentCalendar.get(java.util.Calendar.MONTH) < birthCalendar.get(java.util.Calendar.MONTH)) {
            age--;
        } else if (currentCalendar.get(java.util.Calendar.MONTH) == birthCalendar.get(java.util.Calendar.MONTH)
                && currentCalendar.get(java.util.Calendar.DAY_OF_MONTH) < birthCalendar.get(java.util.Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }

    public String removeSpace(String str) {
        return str.replaceAll(" ","_");
    }

    public String availableSeasonCode(){
        return LocalDate.now().getYear() + "/" + LocalDate.now().getMonth().toString();
    }
}
