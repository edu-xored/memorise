package org.xored.edu.memorise.impl.memo;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.services.MemoEntryDao;
import org.xored.edu.memorise.core.dao.JpaDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * JPA Implementation of a {@link MemoEntryDao}.
 *
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
class JpaMemoEntryDao extends JpaDao<Memo, Long> implements MemoEntryDao {

	public JpaMemoEntryDao() {
		super(Memo.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Memo> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Memo> criteriaQuery = builder.createQuery(Memo.class);

		Root<Memo> root = criteriaQuery.from(Memo.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Memo> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}
}
