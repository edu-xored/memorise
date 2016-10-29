package com.eduxored.memorise.crawler.api;

import com.eduxored.memorise.crawler.MemeCandidate;
import com.eduxored.memorise.crawler.MemeInfo;

/**
 * Created by Anatoly on 26.10.2016.
 */
public interface MemeParser {
    public MemeInfo parse(String text, MemeCandidate candidate);
}
