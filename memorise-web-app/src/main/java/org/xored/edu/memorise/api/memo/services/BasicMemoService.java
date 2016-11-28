package org.xored.edu.memorise.api.memo.services;

import org.xored.edu.memorise.api.memo.Memo;

import java.util.List;

/**
 * Created by Anatoly on 25.11.2016.
 */
public interface BasicMemoService {
    Memo save(Memo memo);

    void delete(Memo memo);

    void delete(Long id);

    List<Memo> findAll();

    Memo find(Long id);
}
