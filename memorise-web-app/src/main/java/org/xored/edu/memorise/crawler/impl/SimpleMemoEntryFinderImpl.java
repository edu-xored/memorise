package org.xored.edu.memorise.crawler.impl;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.api.MemoEntryFinder;

/**
 * Created by Anatoly on 29.10.2016.
 */
public class SimpleMemoEntryFinderImpl implements MemoEntryFinder {
    @Override
    public void findEntries(String text, Memo memo) {
        int counter = 0;
        int index = 0;
        while ((index = indexOfNextEntry(text, memo, index)) != -1) {
            counter++;
        }
        getAndIncrementCounter(memo, counter);
    }

    private void getAndIncrementCounter(Memo memo, int counter) {
        memo.setCounter(memo.getCounter() + counter);
    }

    private int indexOfNextEntry(String text, Memo candidate, int index) {
        String memoTitle = candidate.getTitle();
        return text.indexOf(memoTitle, index + memoTitle.length());
    }
}
