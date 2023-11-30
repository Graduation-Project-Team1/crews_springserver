package com.team1.finalproject.sportsdata.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void setCategory() throws IOException, ParseException, JSONException {
        InputStream inputStream = new ClassPathResource("category.json").getInputStream();
        byte[] arr = inputStream.readAllBytes();
        String s = new String(arr);
        JSONObject object = new JSONObject(s);
        JSONArray jsonArray = (JSONArray) object.get("data");
        JSONObject object1 = (JSONObject) jsonArray.get(0);
        JSONArray jsonArray1 = (JSONArray) object1.get("uniqueTournaments");
        System.out.println("jsonArray1 = " + jsonArray1.get(0));

    }


}