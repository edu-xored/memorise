package org.xored.edu.memorise.api.memo.services;

import java.util.List;

/**
 * Created by Anatoly on 22.11.2016.
 */
public interface SearchMemoService {
    List findMemosByTitle(String title);

    List findMemosByDescription(String description);

    List findMemosContainsTextInTitle(String titleText);

    List findMemosContainsTextInDescription(String descriptionText);
}
