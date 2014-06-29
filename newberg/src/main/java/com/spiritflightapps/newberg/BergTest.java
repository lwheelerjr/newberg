package com.spiritflightapps.newberg;

public class BergTest {

    String name;
    Integer[] scores;

    public BergTest(String name, Integer[] scores) {
        this.name = name;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer[] getScores() {
        return scores;
    }

    public void setScores(Integer[] scores) {
        this.scores = scores;
    }

}
