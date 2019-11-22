package com.examples.sresstesting;

import javafx.util.Pair;

public class TestingResult {
    private Pair<String, Pair<Integer, Integer>> msg;

    public TestingResult(Pair<String, Pair<Integer, Integer>> msg) {
        this.msg = msg;
    }

    public String getURL() {
        return msg.getKey();
    }

    public int getCount() {
        return msg.getValue().getKey();
    }

    public int getTime() {
        return msg.getValue().getValue();
    }
}
