package com.eduxored.memorise.impl.journal;

import com.daniilefremov.core.dao.Dao;
import com.eduxored.memorise.api.journal.Memo;

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