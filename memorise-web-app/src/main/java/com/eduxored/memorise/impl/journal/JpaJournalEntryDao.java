package com.eduxored.memorise.impl.journal;

import com.eduxored.core.dao.JpaDao;
import com.eduxored.memorise.api.journal.Journal;
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
class JpaJournalEntryDao extends JpaDao<Journal, Long> implements JournalEntryDao {

	public JpaJournalEntryDao() {
		super(Journal.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Journal> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Journal> criteriaQuery = builder.createQuery(Journal.class);

		Root<Journal> root = criteriaQuery.from(Journal.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Journal> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}


	@Override
	@Transactional
	public void uploadFile(Journal journal, InputStream bstream) {
		Session session = getEntityManager().unwrap(Session.class);
		try {
			File tempFile = File.createTempFile("temp-file-name", ".tmp");
			tempFile.deleteOnExit();
			FileOutputStream fout = null;
			try {

				fout = new FileOutputStream(tempFile);
				int c;

				while ((c = bstream.read()) != -1) {
					fout.write(c);
				}

			}finally {
				if (bstream != null) {
					bstream.close();
				}
				if (fout != null) {
					fout.close();
				}
			}

			Blob blob = Hibernate.getLobCreator(session).createBlob(new FileInputStream(tempFile), tempFile.length());
			journal.setContent(blob);
			save(journal);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while saving file data to DB for " + journal.toString(), e);
		}
	}

	@Override
	@Transactional
	public InputStream readFile(Journal journal)  {
		try {
			return journal.getContent().getBinaryStream();
		} catch (SQLException e) {
			throw new RuntimeException("no any file found for " + journal,e);
		}
	}

}
