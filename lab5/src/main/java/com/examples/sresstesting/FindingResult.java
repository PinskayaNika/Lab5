package com.examples.sresstesting;

//создаем в actorSystem — актор который принимает две команды —
// поиск уже готового результата тестирования и результат тестрования.

import javafx.util.Pair;

public class FindingResult {
    Pair<String, Integer> msgPair;

    public FindingResult(Pair<String, Integer> pair) {
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
