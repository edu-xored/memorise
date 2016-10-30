package com.eduxored.memorise.crawler.impl;

import com.eduxored.memorise.crawler.MemeCandidate;
import com.eduxored.memorise.crawler.MemeInfo;
import com.eduxored.memorise.crawler.api.MemeParser;

/**
 * Created by Anatoly on 29.10.2016.
 */
public class SimpleMemeParserImpl implements MemeParser {
    @Override
    public MemeInfo parse(String text, MemeCandidate candidate) {
        int counter = 0;
        int index = 0;
        while ((index = indexofNextCandidate(text, candidate, index)) != -1) {
            counter++;
        }
        return new MemeInfo(counter);
    }

    private int indexofNextCandidate(String text, MemeCandidate candidate, int index) {
        return text.indexOf(candidate.getName(), index + candidate.getName().length());
    }
}
