package org.xored.edu.memorise.impl.memo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.services.BasicMemoService;
import org.xored.edu.memorise.api.memo.services.SearchMemoService;
import org.xored.edu.memorise.core.dao.JpaDao;

import java.util.List;

public class JpaBasicMemoService extends JpaDao<Memo, Long> implements BasicMemoService {
    private SearchMemoService searchMemoService;

    public JpaBasicMemoService() {
        super(Memo.class);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "memos", allEntries = true)
    public Memo save(Memo memo) {
        if (memo != null) {
            return super.save(memo);
        }
        throw new IllegalArgumentException("memo must be defined");
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "memos", allEntries = true)
    public void delete(Memo memo) {
        List memosByTitle = searchMemoService.findMemosByTitle(memo.getTitle());
        if (memosByTitle.isEmpty()) {
            throw new IllegalArgumentException("memo must be defined");
        }
        Memo foundMemo = (Memo) memosByTitle.get(0);
        this.delete(foundMemo.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("memos")
    public List<Memo> findAll() {
        return super.findAll();
    }

    @Override
    @Transactional
    public Memo find(Long id) {
        return super.find(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "memos", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Autowired
    public void setSearchMemoService(SearchMemoService searchMemoService) {
        this.searchMemoService = searchMemoService;
    }
}
