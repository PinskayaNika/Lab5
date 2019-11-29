package com.examples.sresstesting;

//создаем в actorSystem — актор который принимает две команды —
// поиск уже готового результата тестирования и результат тестрования.

import javafx.util.Pair;

public class FindingResult {
    private Pair<String, Integer> msgPair;

    FindingResult(Pair<String, Integer> pair) {
        this.msgPair = pair;
    }

    public Pair<String, Integer> getMsgPair() {
        return msgPair;
    }

    String getURL() {
        return msgPair.getKey();
    }

    int getCount() {
        return msgPair.getValue();
    }
}
