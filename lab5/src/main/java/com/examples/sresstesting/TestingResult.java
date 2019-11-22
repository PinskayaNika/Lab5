package com.examples.sresstesting;

import javafx.util.Pair;

public class TestingResult {
    private Pair<String, Pair<Integer, Integer>> msg;

    public TestingResult(Pair<String, Pair<Integer, Integer>> msg) {
        this.msg = msg;
    }

    public Pair<String, Pair<Integer, Integer>> TestingResult() {
        return msg;
    }

    public String getURL() {
        return msg.getKey();
    }

    public int getTime() {
        return msg.getValue().getValue();
    }
}
