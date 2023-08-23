package com.team1.finalproject.sportsdata.repository;

public enum availableRegions {
    /*VALUE_1(1L),//England
    VALUE_2(7L),//France
    VALUE_3(30L),//Germany
    VALUE_4(31L),//Italy
    VALUE_5(32L),//Spain*/
    VALUE_6(291L),//South Korea
    /*VALUE_7(15L),
    VALUE_8(375L),
    VALUE_9(1374L),
    VALUE_10(1385L)*/;

    private final Long id;
    availableRegions(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
