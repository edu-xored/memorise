package org.xored.edu.memorise.impl.journal;

import org.xored.edu.memorise.api.meme.Meme;
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
class JpaJournalEntryDao extends JpaDao<Meme, Long> implements JournalEntryDao {

	public JpaJournalEntryDao() {
		super(Meme.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Meme> findAll()
	{
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Meme> criteriaQuery = builder.createQuery(Meme.class);

		Root<Meme> root = criteriaQuery.from(Meme.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Meme> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}


	@Override
	@Transactional
	public void uploadFile(Meme meme, InputStream bstream) {
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
			meme.setContent(blob);
			save(meme);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error while saving file data to DB for " + meme.toString(), e);
		}
	}

	@Override
	@Transactional
	public InputStream readFile(Meme meme)  {
		try {
			return meme.getContent().getBinaryStream();
		} catch (SQLException e) {
			throw new RuntimeException("no any file found for " + meme,e);
		}
	}

}
