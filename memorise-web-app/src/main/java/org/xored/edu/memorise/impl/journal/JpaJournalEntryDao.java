package org.xored.edu.memorise.impl.journal;

import org.xored.edu.memorise.api.journal.Memo;
import org.xored.edu.memorise.core.dao.JpaDao;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * JPA Implementation of a {@link JournalEntryDao}.
 *
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
class JpaJournalEntryDao extends JpaDao<Memo, Long> implements JournalEntryDao {

	public JpaJournalEntryDao() {
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
