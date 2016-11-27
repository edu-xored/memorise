package org.xored.edu.memorise.impl.memo;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.core.dao.JpaDao;

import java.util.List;

/**
 * Created by Anatoly on 22.11.2016.
 */
public class JpaSearchMemoService extends JpaDao<Memo, String> implements SearchMemoService {

    public JpaSearchMemoService() {
        super(Memo.class);
    }

    @Override
    public List findMemosByTitle(String title) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title = :title")
                .setParameter("title", title)
                .getResultList();
    }

    @Override
    public List findMemosByDescription(String description) {
        return this.getEntityManager().createQuery("select m from Memo m where m.description = :description")
                .setParameter("description", description)
                .getResultList();
    }

    @Override
    public List findMemosContainsTextInTitle(String titleText) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title like :titleText")
                .setParameter("titleText", "%" + titleText + "%")
                .getResultList();
    }

    @Override
    public List findMemosContainsTextInDescription(String descriptionText) {
        return this.getEntityManager().createQuery("select m from Memo m where m.title like :descriptionText")
                .setParameter("descriptionText", "%" + descriptionText + "%")
                .getResultList();
    }
}
