package org.xored.edu.memorise.crawler.impl;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.MemoStatus;
import org.xored.edu.memorise.crawler.api.MemoMatching;

/**
 * Created by Anatoly on 24.11.2016.
 */
public class DummyMemoMatchingImpl implements MemoMatching {
    @Override
    public boolean match(Memo candidate) {
        if (candidate.getCounter() > 5L) {
            candidate.setStatus(MemoStatus.ACTUAL);
            return true;
        }
        else if (candidate.getCounter() < 2L) {
            candidate.setStatus(MemoStatus.ARCHIVED);
        }
        else {
            candidate.setStatus(MemoStatus.CANDIDATE);
        }

        return false;
    }
}
