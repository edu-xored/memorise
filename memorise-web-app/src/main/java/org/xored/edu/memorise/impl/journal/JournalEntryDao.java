package org.xored.edu.memorise.impl.journal;

import org.xored.edu.memorise.api.journal.Memo;
import org.xored.edu.memorise.core.dao.Dao;

import java.io.InputStream;


/**
 * Definition of a Data Access Object that can perform CRUD Operations for {@link Memo}s.
 * 
 * @author Daniil Efremov <daniil.efremov@gmail.com>
 */
public interface JournalEntryDao extends Dao<Memo, Long> {

 	void uploadFile(Memo memo, InputStream bstream);

 	InputStream readFile(Memo memo) ;

}