package com.eduxored.memorise.crawler;

/**
 * Created by Anatoly on 26.10.2016.
 */
public class MemoCandidate {
    private String name;
    private String text;

    public MemoCandidate(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
