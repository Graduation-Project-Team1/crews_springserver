package com.team1.finalproject.dataBuild;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDataTest {
    @Test
    public void mapToList(){
        Map<Long, String> sports = new HashMap<>();
        sports.put(1L, "Football");
        sports.put(2L, "Basketball");
        sports.put(3L, "Baseball");

        List<Long> sportsIdList = new ArrayList<>(sports.keySet());

        System.out.println("sportsIdList = " + sportsIdList);
    }
}
