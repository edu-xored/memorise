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
        while ((index = text.indexOf(candidate.getName())) != -1) {
            counter++;
            text = text.substring(index + candidate.getName().length());
        }
        return new MemeInfo(counter);
    }
}
