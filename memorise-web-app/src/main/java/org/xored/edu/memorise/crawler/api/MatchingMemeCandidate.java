package org.xored.edu.memorise.crawler.api;

import org.xored.edu.memorise.crawler.MemeCandidate;
import org.xored.edu.memorise.crawler.MemeInfo;

/**
 * Created by Anatoly on 26.10.2016.
 */
public interface MatchingMemeCandidate {
    public boolean match(MemeCandidate candidate, MemeInfo info);
}
