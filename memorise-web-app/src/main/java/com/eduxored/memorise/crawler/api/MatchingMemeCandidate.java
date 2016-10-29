package com.eduxored.memorise.crawler;

/**
 * Created by Anatoly on 26.10.2016.
 */
public interface MatchingMemeCandidate {
    public boolean match(MemeCandidate candidate, MemeInfo info);
}
