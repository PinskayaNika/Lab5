package com.examples.sresstesting;

import javafx.util.Pair;

public class TestingResult {
    Pair<String, Integer> msgPair;

    public TestingResult(Pair<Integer, Integer> pair) {
        this.msgPair = pair;
    }

    public Pair<String, Integer> getMsgPair() {
        return msgPair;
    }

    public String getURL() {
        return msgPair.getKey();
    }

    public int getCount() {
        return msgPair.getValue();
    }
}
