package org.xored.edu.memorise.api.memo.services;

import org.xored.edu.memorise.api.memo.Memo;

/**
 * Created by Anatoly on 25.11.2016.
 */
public interface BasicMemoService {
    Memo saveMemo(Memo memo);

    void deleteMemo(Memo memo);
}
