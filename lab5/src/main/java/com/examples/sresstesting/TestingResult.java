package com.examples.sresstesting;

import javafx.util.Pair;

class TestingResult {
    private Pair<String, Pair<Integer, Integer>> msg;

    TestingResult(Pair<String, Pair<Integer, Integer>> msg) {
        this.msg = msg;
    }

    String getURL() {
        return msg.getKey();
    }

    int getCount() {
        return msg.getValue().getKey();
    }

    int getTime() {
        return msg.getValue().getValue();
    }
}
