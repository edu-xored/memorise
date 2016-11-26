package org.xored.edu.memorise.impl.memo;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.core.dao.JpaDao;

/**
 * Created by Anatoly on 26.11.2016.
 */
public class JpaService extends JpaDao<Memo,Long>{

    public JpaService(Class<Memo> entityClass) {
        super(entityClass);
    }
}
