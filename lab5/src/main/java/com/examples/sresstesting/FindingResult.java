package com.examples.sresstesting;

//создаем в actorSystem — актор который принимает две команды —
// поиск уже готового результата тестирования и результат тестрования.

import javafx.util.Pair;

public class FindingResult {
    //private Pair<String, Integer> msgPair;
    private String msgUrl;
    private int msgCount;

    FindingResult(String url, int count) {
        this.msgUrl = url;
        this.msgCount = count;
    }


    String getURL() {
        return msgUrl;
    }

    int getCount() {
        return msgCount;
    }
}
