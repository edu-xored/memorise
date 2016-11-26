package org.xored.edu.memorise.impl.memo;

import org.springframework.transaction.annotation.Transactional;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.services.SearchMemoService;
import org.xored.edu.memorise.core.dao.JpaDao;

import java.util.List;

/**
 * Created by Anatoly on 22.11.2016.
 */
public class JpaSearchMemoService extends JpaDao<Memo, Long> implements SearchMemoService {

    public JpaSearchMemoService() {
        super(Memo.class);
    }

    @Override
    @Transactional
    public List<Memo> findMemosByTitle(String title) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title = :title")
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Memo> findMemosByDescription(String description) {
        return this.getEntityManager().createQuery("select m from Memo m where m.description = :description")
                .setParameter("description", description)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Memo> findMemosContainsTextInTitle(String titleText) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title like :titleText")
                .setParameter("titleText", "%" + titleText + "%")
                .getResultList();
    }

    @Override
    @Transactional
    public List<Memo> findMemosContainsTextInDescription(String descriptionText) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title like :descriptionText")
                .setParameter("descriptionText", "%" + descriptionText + "%")
                .getResultList();
    }
}
