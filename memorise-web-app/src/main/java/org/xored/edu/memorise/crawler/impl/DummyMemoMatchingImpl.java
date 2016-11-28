package org.xored.edu.memorise.crawler.impl;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.MemoStatus;
import org.xored.edu.memorise.crawler.api.MemoMatching;

/**
 * Created by Anatoly on 24.11.2016.
 */
public class DummyMemoMatchingImpl implements MemoMatching {

    public static final long ACTUAL_COUNTER = 5L;
    public static final long ARCHIVED_COUNTER = 2L;

    @Override
    public boolean match(Memo candidate) {
        if (candidate.getCounter() > ACTUAL_COUNTER) {
            candidate.setStatus(MemoStatus.ACTUAL);
            return true;
        }
        else if (candidate.getCounter() < ARCHIVED_COUNTER) {
            candidate.setStatus(MemoStatus.ARCHIVED);
        }
        else {
            candidate.setStatus(MemoStatus.CANDIDATE);
        }

        return false;
    }
}
