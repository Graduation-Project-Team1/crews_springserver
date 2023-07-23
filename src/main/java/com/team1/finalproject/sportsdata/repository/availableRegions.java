package com.team1.finalproject.sportsdata.repository;

public enum availableRegions {
    VALUE_1(1L),
    VALUE_2(7L),
    VALUE_3(30L),
    VALUE_4(31L),
    VALUE_5(32L),
    VALUE_6(291L),
    VALUE_7(15L),
    VALUE_8(375L),
    VALUE_9(1374L),
    VALUE_10(1385L);

    private final Long id;
    availableRegions(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
