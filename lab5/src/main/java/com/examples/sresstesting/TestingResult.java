package com.examples.sresstesting;

import javafx.util.Pair;

class TestingResult {
    //private Pair<String, Pair<Integer, Integer>> msg;
    private String msgUrl;
    private Integer msgCount;
    private Integer msgTime;

    TestingResult(String url, Integer count, Integer time) {
        this.msgUrl = url;
        this.msgCount = count;
        this.msgTime = time;

    }

    String getURL() {
        return msgUrl;
    }

    int getCount() {
        return msgCount;
    }

    int getTime() {
        return msgTime;
    }
}
