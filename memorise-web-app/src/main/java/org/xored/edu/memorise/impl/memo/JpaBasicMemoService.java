package org.xored.edu.memorise.impl.memo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.BasicMemoService;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.core.dao.JpaDao;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Anatoly on 25.11.2016.
 */
public class JpaBasicMemoService extends JpaDao<Memo, Long> implements BasicMemoService {
    private SearchMemoService searchMemoService;

    public JpaBasicMemoService() {
        super(Memo.class);
    }

    @Override
    @Transactional
    public Memo saveMemo(Memo memo) {
        return this.save(memo);
    }

    @Override
    public void deleteMemo(Memo memo) {
        List memosByTitle = searchMemoService.findMemosByTitle(memo.getTitle());
        if (memosByTitle.isEmpty()) {
            return;
        }
        Memo foundMemo = (Memo) memosByTitle.get(0);
        this.delete(foundMemo.getId());
    }

    @Autowired
    public void setSearchMemoService(SearchMemoService searchMemoService) {
        this.searchMemoService = searchMemoService;
    }
}
