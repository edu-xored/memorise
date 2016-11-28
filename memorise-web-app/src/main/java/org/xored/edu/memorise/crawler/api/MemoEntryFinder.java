package org.xored.edu.memorise.crawler.api;

import org.xored.edu.memorise.api.memo.Memo;

/**
 * Created by Anatoly on 26.10.2016.
 */
public interface MemoEntryFinder {
    void findEntries(String text, Memo candidate);
}
