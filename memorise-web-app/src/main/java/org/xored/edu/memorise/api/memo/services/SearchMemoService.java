package org.xored.edu.memorise.api.memo.services;

import org.xored.edu.memorise.api.memo.Memo;

import java.util.List;

/**
 * Created by Anatoly on 22.11.2016.
 */
public interface SearchMemoService {
    List<Memo> findMemosByTitle(String title);

    List<Memo> findMemosByDescription(String description);

    List<Memo> findMemosContainsTextInTitle(String titleText);

    List findMemosContainsTextInDescription(String descriptionText);
}
