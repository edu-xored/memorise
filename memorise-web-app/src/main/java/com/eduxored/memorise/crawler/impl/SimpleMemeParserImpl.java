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
        int index;
        while ((index = text.indexOf(candidate.getName())) != -1) {
            counter++;
            text = sliceCandidateFromText(text, candidate, index);
        }
        return new MemeInfo(counter);
    }

    private String sliceCandidateFromText(String text, MemeCandidate candidate, int index) {
        return text.substring(index + candidate.getName().length());
    }
}
