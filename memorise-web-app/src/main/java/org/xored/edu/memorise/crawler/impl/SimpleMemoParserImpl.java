package org.xored.edu.memorise.crawler.impl;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.api.MemoParser;

/**
 * Created by Anatoly on 29.10.2016.
 */
public class SimpleMemoParserImpl implements MemoParser {
    @Override
    public void parse(String text, Memo candidate) {
        int counter = 0;
        int index = 0;
        while ((index = indexofNextCandidate(text, candidate, index)) != -1) {
            counter++;
        }
        candidate.setCounter(candidate.getCounter() + counter);
    }

    private int indexofNextCandidate(String text, Memo candidate, int index) {
        String memoTitle = candidate.getTitle();
        return text.indexOf(memoTitle, index + memoTitle.length());
    }
}
